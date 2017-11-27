<template>
    <div>

        <div class="modal fade show">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Delete <span class="badge badge-danger">NO. {{ modal.widget.weight }}</span> {{ modal.widget.description }}</h5>
                        <button @click.prevent="modal.delete = false" type="button" class="close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Please confirm you wish to delete <b>{{ modal.widget.description }}</b> by clicking the <span class="badge badge-danger">Confirm Widget Deletion</span> button below.</p>
                    </div>
                    <div class="modal-footer">
                        <button @click.prevent="modal.delete = false" type="button" class="btn btn-secondary">Cancel</button>
                        <button @click.prevent="deleteWidget" type="button" class="btn btn-danger">Confirm Widget Deletion</button>
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
            deleteWidget(){
                let vm = this;

                const data = {
                    id : vm.modal.widget.id,
                }

                console.log('Delete Widget');
                console.log(data);

                axios.post('/api/v1/widgets/delete', data)
                .then( response => {
                    Bus.$emit('deleteWidgetSuccess', true);
                    vm.modal.widget = {};
                    vm.modal.delete = false;
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