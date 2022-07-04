import {createStore} from 'vuex'

function toBoolean(text) {
    return text === 'true';
}

export default createStore({
    state: {
        login: false,
        theme: {
            basic: {},
            home: {}
        }
    },
    mutations: {
        updateTheme(state, data) {
            state.theme.basic = {
                dark: toBoolean(data.dark),
                textSize: data.textSize,
                color: data.color,
            };
            state.theme.home = {
                autoImage: toBoolean(data.autoImage),
                switchImage: toBoolean(data.switchImage),
                imageSource: data.imageSource,
                markStyle: data.markStyle,
                serviceStyle: data.serviceStyle,
                toolsStyle: data.toolsStyle,
                phrase: toBoolean(data.phrase),
                phraseApi: data.phraseApi
            };
        },
        updateLogin(state, data){
            state.login = data;
        }
    },
    actions: {},
    modules: {}
})
