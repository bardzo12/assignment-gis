<template>
    <div>

        <div class="modal fade show">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Edit <span class="badge badge-success">NO. {{ modal.widget.weight }}</span> {{ modal.widget.description }}</h5>
                        <button @click.prevent="modal.edit = false" type="button" class="close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label>Widget Name</label>
                            <input type="text" v-model="modal.widget.description" class="form-control" placeholder="Enter widget name">
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button @click.prevent="modal.edit = false" type="button" class="btn btn-secondary">Cancel</button>
                        <button @click.prevent="editWidget" type="button" class="btn btn-success">Edit Widget</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>
<script>
/**
*
*/
    export default{
        template: '<widget-delete></widget-delete>',
        props: ['modal'],
        data(){
            return{

            }
        },
        mounted(){

        },
        computed:{

        },
        watch:{

        },
        methods:{
            editWidget(){
                let vm = this;

                const data = {
                    id : vm.modal.widget.id,
                    description : vm.modal.widget.description
                }

                console.log('Edit Widget');
                console.log(data);

                axios.post('/api/v1/widgets/edit', data)
                .then( response => {
                    Bus.$emit('editWidgetSuccess', true);
                    vm.modal.widget = {};
                    vm.modal.edit = false;
                    vm.$destroy(true);
                })
                .catch( error => {
                    console.log('failure');
                })
            }
        },
        components:{
            //'sample-component':                    require('./sample-component.vue'),
        }
    }
</script>