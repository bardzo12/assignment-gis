<template>
    <div>

        <div class="modal fade show">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Add a Widget</h5>
                        <button @click.prevent="modal.add = false" type="button" class="close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Widget Name</label>
                            <input type="text" v-model="widget" class="form-control" placeholder="Enter widget name">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button @click.prevent="modal.add = false" type="button" class="btn btn-secondary">Cancel</button>
                        <button @click.prevent="addWidget" type="button" class="btn btn-success">Add Widget</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
/**
* Add a Widget
*/
    export default{
        template: '<widget-add></widget-add>',
        props: ['widget-count','modal'],
        data(){
            return{
                widget: '',
            }
        },
        mounted(){

        },
        computed:{

        },
        watch:{

        },
        methods:{
            addWidget(){
                let vm = this;

                const data = {
                    description : vm.widget,
                    weight : vm.widgetCount + 1
                }

                console.log('Adding Widget');
                console.log(data);

                axios.post('/api/v1/widgets/add', data)
                .then( response => {
                    Bus.$emit('addWidgetSuccess', true);
                    vm.modal.add = false;
                    vm.$destroy(true);
                })
                .catch( error => {
                    console.log('failure');
                })
            },
        },
        components:{
            //'sample-component':                    require('./sample-component.vue'),
        }
    }
</script>