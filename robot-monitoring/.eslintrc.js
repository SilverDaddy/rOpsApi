module.exports = {
    "env": {
        "browser": true,
        "es6": true,
		"node": true
    },
    "extends": [
        "eslint:recommended",
        "plugin:vue/essential"
    ],
    "globals": {
        "Atomics": "readonly",
        "SharedArrayBuffer": "readonly"
    },
    "parserOptions": {
        "parser": "babel-eslint",
		"ecmaVersion": 2020,
		"sourceType": "module"
    },
    "plugins": [
        "vue"
    ],
    "rules": {
		'vue/valid-v-slot': ['error', { allowModifiers: true }]
    },
	parser: 'vue-eslint-parser'
};