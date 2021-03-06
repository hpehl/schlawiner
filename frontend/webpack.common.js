const path = require('path');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const WebpackShellPlugin = require('webpack-shell-plugin');

const DIST = process.env.DIST || 'target/classes/org/jboss/schlawiner/public';

module.exports = {
    entry: {
        external: './src/main/web/main.js'
    },

    plugins: [
        new CleanWebpackPlugin([DIST]),
        new HtmlWebpackPlugin({
            inject: 'head',
            favicon: './src/main/web/favicon.ico',
            template: './src/main/web/index.html'
        }),
        new MiniCssExtractPlugin({
            filename: 'schlawiner.css',
            chunkFilename: '[id].css'
        }),
        new WebpackShellPlugin({onBuildStart: ['./protojs.sh']})
    ],

    module: {
        rules: [
            {
                test: /\.(png|jpg|gif)$/i,
                use: [{
                    loader: 'url-loader',
                    options: {
                        limit: 8192
                    }
                }]
            },
            {test: /\.svg$/, use: 'file-loader'},
            {test: /\.ico$/, loader: 'file-loader?name=[name].[ext]'},
            {
                test: /\.(woff(2)?|ttf|eot)$/,
                use: [{
                    loader: 'file-loader',
                    options: {
                        name: '[name].[ext]',
                        outputPath: 'webfonts/'
                    }
                }]
            },
            {
                test: /\.css$/,
                use: [
                    {loader: MiniCssExtractPlugin.loader},
                    {loader: 'css-loader'},
                    {loader: 'postcss-loader'}
                ]
            },
            {
                test: /\.less$/,
                use: [
                    {loader: MiniCssExtractPlugin.loader},
                    {loader: 'css-loader'},
                    {loader: 'postcss-loader'},
                    {loader: 'less-loader'}
                ]
            }
        ]
    },

    output: {
        path: path.resolve(__dirname, DIST),
        filename: '[name].js'
    }
}
