<template>
    <div class="card">
        <div class="card-header">Widgets</div>
        <div class="card-block">
            Hello World
        </div>
        <div class="card-footer text-right text-muted p-0">
            <a href="#" class="btn btn-primary mr-2 mt-2 mb-2"><i class="material-icons base-font-size">add</i> Add Widget</a>
        </div>
    </div>
</template>
<script>
/**
*
*/
    import draggable from 'vuedraggable'
    export default{
        template: '<widgets></widgets>',
        //props: ['prop1','prop2'],
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

        },
        components:{
            draggable,
            //'sample-component':                    require('./sample-component.vue'),
        }
    }
</script>