$(function () {


   var _height = $('.divBox').eq(0).find('.switchIn').height();
   var height = _height + 45 + 70;
   vm.h = height;
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q:{
            username: null
        },
        showList: true,
        title:null,
        roleList:{},
        user:{
            status:1,
            deptId:null,
            deptName:null,
            roleIdList:[]
        },
        open:true,
        openText:'展开筛选',
        h:0,
        tableList:[],
        totalPage:0,
        page:1,
        safeLevelList:[],
        fileData:{
            name:'',
            safe:{}
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getUser: function(userId){
            $.get(baseURL + "sys/user/info/"+userId, function(r){
                vm.user = r.user;
                vm.user.password = null;

                vm.getDept();
            });
        },
        getRoleList: function(){
            $.get(baseURL + "sys/role/select", function(r){
                vm.roleList = r.list;
            });
        },
        reload: function () {
            vm.getList();
        },
        clean:function () {
            vm.q.username = null
        },
        // 收缩展开搜索
        openSwitch:function () {
            if(vm.open){
                vm.open = false;
                vm.openText = '收起筛选'

            }else {
                vm.open = true;
                vm.openText = '展开筛选'
            }
        },
        // 获取表格数据
        getList:function(){
            $.ajax({
                url:baseURL + 'sys/user/list',
                dataType: 'json',
                data: {
                    username:this.q.username,
                    page:this.page
                },
                success: function(res){
                    console.log(res);
                    if(res.code == 0){
                        vm.tableList = res.page.list;
                        vm.totalPage = res.page.totalCount;
                        console.log('111111');
                        console.log(vm.tableList)
                    }else{
                        alert(res.msg);
                    }
                }
            })
        },

        // 分页
        layerPage:function (currentPage) {
            console.log(currentPage);
            vm.page = currentPage;
            vm.getList();
        },
        // 设置安全级别
        setSafe: function (row) {
            vm.fileData = {
                name:'',
                safe:null
            }
            vm.user = row;
            vm.fileData.name = row.name;
            console.log(vm.fileData)
            layer.open({
                type: 1,
                title: '设置',
                content: $('#safeDiv'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['500px', '260px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    vm.user.safe = vm.fileData.safe.value;
                    vm.user.safeCode = vm.fileData.safe.code;
                    $.ajax({
                        type: "POST",
                        url: baseURL + 'sys/user/update',
                        contentType: "application/json",
                        data: JSON.stringify(vm.user),
                        success: function(r){
                            if(r.code === 0){
                                layer.close(index);
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }else{
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                },
                btn2:function () {

                }
            })

        },
        // 数据字典
        dictClick:function (type) {
            $.ajax({
                type: "get",
                url: baseURL + "sys/dict/selectDict",
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    type:type
                },
                success: function(r){
                    if(type == 'safe_level'){
                        vm.safeLevelList = r;
                    }
                }
            });
        },
    },
    created:function(){
        this.getList()
        this.dictClick('safe_level');
    },
});