$(function () {


})

var vm = new Vue({
    el:'#rrapp',
    data:{
        textarea:'',
        resourcemeteData:{},
        catalogsearchData:{},
        resourcecatalogData:{},
        format:''
    },
    methods:{
        // 元数据统计
        resourcemetedata:function () {
            $.ajax({
                type: "GET",
                url: baseURL + "xj/xjcatalog/count1",
                success: function(r){
                    if(r.code == 0){
                        console.log(r);
                        vm.resourcemeteData = r.countDTO;

                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 访问量统计
        catalogsearch:function () {
            $.ajax({
                type: "GET",
                url: baseURL + "xj/xjcatalog/count2",
                success: function(r){
                    if(r.code == 0){
                        console.log(r);
                        vm.catalogsearchData = r;
                        ziyuan(vm.catalogsearchData);
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 目录统计
        resourcecatalog:function () {
            $.ajax({
                type: "GET",
                url: baseURL + "resource/resourcecatalog/stat",
                success: function(r){
                    if(r.code == 0){
                        console.log(r);
                        vm.resourcecatalogData = r;
                        ziyuan1(vm.resourcecatalogData);
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        }
    },
    created:function(){
        this.resourcemetedata();
        this.catalogsearch();
        // this.resourcecatalog();
    }
})