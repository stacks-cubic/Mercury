const isProduction = process.env.NODE_ENV === 'production'

module.exports = {
    productionSourceMap: !isProduction,
    css: {
        sourceMap: !isProduction,
        loaderOptions: {
            less: {
                lessOptions: {
                    modifyVars: {
                        'border-radius-base': '8px',
                    },
                    javascriptEnabled: true,
                },
            },
        },
    },
    configureWebpack: {
        optimization: {
            runtimeChunk: 'single',
            splitChunks: {
                chunks: 'all',
                maxAsyncRequests: Infinity,
                maxInitialRequests: Infinity,
                minSize: 32768,
                cacheGroups: {
                    vendor: {
                        test: /[\\/]node_modules[\\/]/,
                        name(module) {
                            const packageName = module.context.match(/[\\/]node_modules[\\/](.*?)([\\/]|$)/)[1]
                            return packageName.replace('@', '')
                        }
                    }
                }
            }
        }
    },
    outputDir: '../src/main/resources/static'
}