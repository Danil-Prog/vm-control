const HtmlWebpackPlugin = require('html-webpack-plugin');
const path = require('path');

module.exports = {
  entry: './src/index.tsx',
  mode: 'development',
  output: {
    filename: 'bundle.[fullhash].js',
    path: path.resolve(__dirname, 'dist'),
    publicPath: '/'
  },
  plugins: [
    new HtmlWebpackPlugin({
      template: './src/index.html'
    })
  ],
  resolve: {
    alias: {
      '~': path.resolve(__dirname, 'src')
    },
    modules: [__dirname, 'src', 'node_modules'],
    extensions: ['.*', '.js', '.jsx', '.tsx', '.ts']
  },
  module: {
    rules: [
      {
        test: /\.(js|ts)x?$/,
        exclude: /node_modules/,
        use: ['babel-loader']
      },
      {
        test: /\.(css|s[ac]ss)$/,
        exclude: /node_modules/,
        use: ['style-loader', 'css-loader', 'sass-loader']
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        exclude: /node_modules/,
        use: ['file-loader']
      }
    ]
  },
  devServer: {
    historyApiFallback: true,
    port: 3000
  }
};