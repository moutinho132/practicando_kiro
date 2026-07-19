const ModuleFederationPlugin = require('@angular-architects/module-federation/webpack');

module.exports = {
  output: {
    uniqueName: 'shell',
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
      name: 'shell',
      remotes: {
        mfeCaptura: 'http://localhost:4201/remoteEntry.js',
        mfeConsulta: 'http://localhost:4202/remoteEntry.js'
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
