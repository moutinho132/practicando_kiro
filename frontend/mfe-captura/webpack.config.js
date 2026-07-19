const ModuleFederationPlugin = require('@angular-architects/module-federation/webpack');

module.exports = {
  output: {
    uniqueName: 'mfeCaptura',
    publicPath: 'auto',
    scriptType: 'text/javascript'
  },
  optimization: {
    runtimeChunk: false
  },
  resolve: {
    alias: {
      ...require('@angular-architects/module-federation/webpack').alias
    }
  },
  experiments: {
    outputModule: true
  },
  plugins: [
    new ModuleFederationPlugin({
      name: 'mfeCaptura',
      filename: 'remoteEntry.js',
      exposes: {
        './Module': './src/app/captura.module.ts'
      },
      shared: {
        '@angular/core': { singleton: true, strictVersion: true, requiredVersion: '^19.0.0' },
        '@angular/common': { singleton: true, strictVersion: true, requiredVersion: '^19.0.0' },
        '@angular/common/http': { singleton: true, strictVersion: true, requiredVersion: '^19.0.0' },
        '@angular/router': { singleton: true, strictVersion: true, requiredVersion: '^19.0.0' }
      }
    })
  ]
};
