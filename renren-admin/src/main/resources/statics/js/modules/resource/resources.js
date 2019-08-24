$(function () {
    ziyuan();
})

var vm = new Vue({
    el:'#rrapp',
    data:{
        textarea:''
    },
    methods:{
        // 元数据统计
        resourcemetedata:function () {
            $.ajax({
                type: "GET",
                url: baseURL + "resource/resourcemetedata/stat",
                success: function(r){
                    if(r.code == 0){
                        vm.gatntObj.userId = r.userList;
                        layer.open({
                            type: 1,
                            title: '授权',
                            content: $('#grant'), //这里content是一个普通的String
                            skin: 'openClass',
                            area: ['562px', '520px'],
                            shadeClose: true,
                            closeBtn:0,
                            btn: ['确定','取消'],
                            btn1:function (index) {
                                vm.setGrant(id);
                                layer.close(index);
                            },
                            btn2:function () {
                                vm.reload();
                            }
                        })
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 访问量统计
        catalogsearch:function () {

        },
        // 目录统计
        resourcecatalog:function () {
            
        }
    },
    created:function(){

    }
})