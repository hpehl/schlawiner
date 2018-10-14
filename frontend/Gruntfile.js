/*
 * Copyright 2015-2016 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
module.exports = function (grunt) {
    'use strict';

    // load all grunt tasks
    require('matchdep').filterDev('grunt-*').forEach(grunt.loadNpmTasks);

    // Project configuration.
    grunt.initConfig({
        config: {
            devmodeTarget: 'target/gwt/devmode/war/schlawiner',
            less: 'src/less',
            node: 'node_modules',
            public: 'src/main/resources/org/jboss/schlawiner/public',
            version: '0.5'
        },

        clean: {
            public: [
                '<%= config.public %>/schlawiner*.css',
                '<%= config.public %>/external*.js'
            ]
        },

        copy: {
            css: {
                files: [
                    {
                        expand: true,
                        cwd: '<%= config.public %>',
                        src: 'schlawiner.css',
                        dest: '<%= config.devmodeTarget %>'
                    }
                ]
            }
        },

        concat: {
            external: {
                options: {
                    banner: '/*!\n' +
                        ' * External JS files for Schlawiner <%= config.version %>\n' +
                        ' * Build date: <%= grunt.template.today("yyyy-mm-dd HH:MM:ss") %>\n' +
                        ' */\n\n',
                    stripBanners: true
                },
                nonull: true,
                filter: function (filepath) {
                    if (!grunt.file.exists(filepath)) {
                        grunt.fail.fatal('Grunt error. Could not find: ' + filepath);
                    } else {
                        return true;
                    }
                },
                src: [
                    '<%= config.node %>/raphael/raphael.min.js',
                    '<%= config.node %>/tingle.js/dist/tingle.js'
                ],
                dest: '<%= config.public %>/external.js'
            }
        },

        less: {
            target: {
                options: {
                    banner: '/*\n' +
                    ' * Generated CSS file for Schlawiner <%= config.version %>\n' +
                    ' * Build date: <%= grunt.template.today("yyyy-mm-dd HH:MM:ss") %>\n' +
                    ' */\n\n',
                    paths: '<%= config.less %>',
                    strictMath: false
                },
                src: '<%= config.less %>/schlawiner.less',
                dest: '<%= config.public %>/schlawiner.css'
            }
        },

        postcss: {
            target: {
                options: {
                    processors: [
                        require('autoprefixer')({browsers: ['last 3 versions', 'ie 9']})
                    ]
                },
                files: [{
                    expand: true,
                    cwd: '<%= config.public %>',
                    src: 'schlawiner.css',
                    dest: '<%= config.public %>'
                }]
            }
        },

        cssmin: {
            target: {
                files: [{
                    expand: true,
                    cwd: '<%= config.public %>',
                    src: ['*.css', '!*.min.css'],
                    dest: '<%= config.public %>',
                    ext: '.min.css'
                }]
            }
        },

        watch: {
            less: {
                files: ['<%= config.less %>/*.less'],
                tasks: ['less']
            }
        }
    });

    grunt.registerTask('css', [
        'less',
        'postcss',
        'copy:css'
    ]);

    grunt.registerTask('dev', [
        'clean',
        'concat:external',
        'less',
        'postcss'
    ]);

    grunt.registerTask('prod', [
        'clean',
        'concat:external',
        'less',
        'postcss',
        'cssmin'
    ]);

    grunt.registerTask('default', ['dev']);
};
