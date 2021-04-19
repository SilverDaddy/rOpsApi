<template>
    <v-layout row wrap>
        <v-container fluid>
            <v-data-table
            :headers="headers"
            :items="results"
            class="elevation-1"
            :items-per-page="15"
            >
                <template v-slot:top>
                    <v-toolbar flat color="white">
                    <v-toolbar-title>RPA Bot Table</v-toolbar-title>
                    <div class="flex-grow-1"></div>
                    <v-dialog v-model="dialog" max-width="500px">
                        <template v-slot:activator="{ on }">
                            <v-btn class="ma-2" tile outlined color="success" v-on="on">
                                <v-icon left>mdi-pencil</v-icon> New Bot
                            </v-btn>
                        </template>
                        <v-card>
                        <v-card-title>
                            <span class="headline">{{ formTitle }}</span>
                        </v-card-title>
            
                        <v-card-text>
                            <v-container grid-list-md>
                                <v-form ref="form" v-model="valid" lazy-validation>
                                    <v-layout row wrap>
                                        <v-flex xs3>
                                            <v-text-field v-model="editedItem.bot_id" label="Bot ID" :rules="[rules.required, rules.xss]"></v-text-field>
                                        </v-flex>
                                        <v-flex xs9>
                                            <v-text-field v-model="editedItem.bot_name" label="Bot Name" :rules="[rules.required, rules.xss]"></v-text-field>
                                        </v-flex>
                                        <v-flex xs12>
                                            <v-text-field v-model="editedItem.description" label="Description" :rules="[rules.xss]"></v-text-field>
                                        </v-flex>
                                    </v-layout>
                                </v-form>
                            </v-container>
                        </v-card-text>
            
                        <v-card-actions>
                            <div class="flex-grow-1"></div>
                            <v-btn color="teal darken-1" text @click="close">Cancel</v-btn>
                            <v-btn color="teal darken-1" text @click="save">Save</v-btn>
                        </v-card-actions>
                        </v-card>
                    </v-dialog>
                    </v-toolbar>
                </template>

                <template v-slot:item.action="{ item }">
                    <v-icon
                    small
                    class="mr-2"
                    @click="editItem(item)"
                    >
                    edit
                    </v-icon>
                    <v-icon
                    small
                    @click="deleteItem(item)"
                    >
                    delete
                    </v-icon>
                </template>
            </v-data-table>
        </v-container>
    </v-layout>
</template>

<script>
export default {
    data() {
        return {
            dialog: false,
            valid: true,
            headers: [
                { text: 'Bot ID', value: 'bot_id' },
                { text: 'Bot Name', value: 'bot_name' },
                { text: 'Description', value: 'description' },
                { text: 'Actions', value: 'action', sortable: false }
            ],
            results: [],
            editedIndex: -1,
            editedItem: {
                bot_id: '',
                bot_name: '',
                description: ''
            },
            defaultItem: {
                bot_id: '',
                bot_name: '',
                description: ''
            },
            rules: {
                required: value => !!value || '필수 입력 항목',
                xss: value => {
                    const pattern = /[<>"'&]/
                    return !pattern.test(value) || '사용금지 문자'
                },
            },
        }
    },
    mounted () {
        this.axios.get(this.$store.state.rOpsApi+'/bots').then(response => {
            this.results = response.data
        })
    },
    computed: {
        formTitle () {
            return this.editedIndex === -1 ? 'New Bot' : 'Edit Bot'
        }
    },
    watch: {
        dialog (val) {
            val || this.close()
            setTimeout(() => {
                this.resetValidation()
            }, 300)
        }
    },
   
    methods: {
        editItem (item) {
            this.editedIndex = this.results.indexOf(item)
            this.editedItem = Object.assign({}, item)
            this.dialog = true
        },

        deleteItem (item) {
            const index = this.results.indexOf(item)
            confirm('Are you sure you want to delete this bot?') && this.results.splice(index, 1) &&
            this.axios.delete(this.$store.state.rOpsApi+'/admin/bots/'+item.bot_id)
        },

        close () {
            this.dialog = false
            setTimeout(() => {
                this.editedItem = Object.assign({}, this.defaultItem)
                this.editedIndex = -1
            }, 300)
        },

        save () {
            if (this.validate()) {
                if (this.editedIndex > -1) {
                    // 수정
                    this.axios.patch(this.$store.state.rOpsApi+'/admin/bots/'+this.editedItem.bot_id,this.editedItem).then(
                        Object.assign(this.results[this.editedIndex], this.editedItem)
                    )
                } else {
                    // 신규     
                    this.axios.post(this.$store.state.rOpsApi+'/admin/bots',this.editedItem).then(
                        this.resluts.push(this.editedItem)
                    )
                }
                this.close()
            }
        },

        validate () {
            return this.$refs.form.validate()
        },

        resetValidation () {
            this.$refs.form.resetValidation()
        }
    }
}
</script>

<style scoped>

</style>