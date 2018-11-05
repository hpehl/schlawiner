module.exports = ({ file, options, env }) => ({
    plugins: {
        'postcss-unprefix': {},
        'autoprefixer': {},
        'cssnano': env === 'production' ? {} : false
    }
})
