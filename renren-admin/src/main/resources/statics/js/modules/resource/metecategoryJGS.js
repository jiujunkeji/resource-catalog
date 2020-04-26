$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;


});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "catalogId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var setting1 = {
    data: {
        simpleData: {
            enable: true,
            idKey: "meteCategoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree1;

var vm = new Vue({
    el:'#rrapp',
    data:{
        q: {
            cnName:'',
            meteSetNumber:'',
            meteCategorySetId:'',

        },
        q1: {
            name:'',
            meteNumber:'',
            meteCategoryId:'',
            cnName:''
        },
        showList: true,
        title: null,
        resourceMeteData: {
            meteDataList:[]
            // meteCategorySetId:null
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        open1:true,
        openText1:'展开筛选',
        h:0,
        props: {
            label: 'name',
            children: 'list',
        },
        count: 1,
        id:0,
        filterText:'',
        menuList:[],
        menuList1:[],
        tableList:[],
        tableList1:[],
        totalPage:0,
        totalPage1:0,
        page:1,
        page1:1,
        pageSize:10,
        tab:0,
        checkIdList:[],
        checkIdList1:[],
        checkIdList2:[],
        catalogId:null,
        fileData:{},
        comList:[],
        fenlSelect:[],
        fenlSelect1:[],
        hisList:[],
        optionList:[],
        auditOpinion:''
    },
    watch: {
        filterText:function(val) {
            this.$refs.tree.filter(val);
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        clean:function () {
            vm.q = {
                cnName:'',
                meteSetNumber:'',
                meteCategorySetId:'',

            }
        },
        getMenu: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/resourcecatalog/list", function(r){
                console.log(r);
                // r.push({
                //     parentId:-1,
                //     catalogId:0,
                //     name:'一级目录'
                // })
                ztree = $.fn.zTree.init($("#menuTree"), setting, r);
                var node = ztree.getNodeByParam("catalogId", vm.resourceMeteData.catalogId);
                ztree.selectNode(node);
                console.log(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    console.log(node);
                    //选择上级菜单
                    vm.resourceMeteData.catalogId = node[0].catalogId;
                    vm.resourceMeteData.catalogName = node[0].name;
                    // vm.resourceMeteData.catagoryCode = node[0].code;
                    layer.close(index);
                }
            });
        },
        getMenu1: function(menuId){
            //加载菜单树
            $.get(baseURL + "resource/metecategory/list", function(r){
                console.log(r);
                // r.push({
                //     parentId:-1,
                //     meteCategoryId:0,
                //     name:'一级目录'
                // })
                ztree1 = $.fn.zTree.init($("#menuTree1"), setting1, r);
                var node = ztree1.getNodeByParam("meteCategoryId", vm.resourceMeteData.categoryId);
                ztree1.selectNode(node);
                console.log(node);
                // vm.menu.parentName = node.name;
            })
        },
        menuTree1: function(){
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择菜单",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#menuLayer1"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree1.getSelectedNodes();
                    console.log(node);
                    //选择上级菜单
                    vm.resourceMeteData.categoryId = node[0].meteCategoryId;
                    vm.resourceMeteData.categoryName = node[0].name;
                    vm.resourceMeteData.catagoryCode = node[0].code;
                    console.log(vm.resourceMeteData.catagoryCode);
                    layer.close(index);
                }
            });
        },
        add: function(){

            vm.showList = false;
            vm.title = "新增元数据集";
            // vm.resourceMeteData = {
            //     meteType:null,
            //     categoryId:null,
            //     categoryName:'',
            //     catagoryCode:'',
            //     catalogId:'',
            //     catalogName:'',
            //     fieldList:[]
            // };

        },
        update: function (id) {
            var meteId = id;
            if(meteId == null){
                return ;
            }
            //
            vm.showList = false;
            vm.title = "修改元数据集";

            vm.getInfo(meteId);

        },
        saveOrUpdate: function (event) {
            var url = vm.resourceMeteData.meteSetId == null ? "xj/xjmetadataset/save" : "xj/xjmetadataset/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.resourceMeteData),
                success: function(r){
                    if(r.code === 0){
                        vm.page = 1;
                        vm.reload();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:100});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        del: function (id) {
            var list = [];
            list.push(id)

            layer.confirm('确定要删除选中的记录？', function(index){
                $.ajax({
                    type: "POST",
                    url: baseURL + "xj/xjmetadataset/delete",
                    contentType: "application/json",
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code == 0){
                            layer.close(index);
                            vm.reload();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px'],time:1000});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            });
        },
        getInfo: function(meteId){
            $.get(baseURL + "xj/xjmetadataset/info/"+meteId, function(r){
                console.log(r);
                vm.resourceMeteData = r.xjMetaDataSet;
                // vm.tableListUp = r.resourceMeteData.list;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.getTableList();
        },
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
        // 获取资源提供方单位
        getComList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/organisationinfo/select',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:1,
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.comList = r.list;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 设置资源提供方信息
        setCom:function (obj) {
            console.log(obj);
            vm.resourceMeteData.organisationName = obj.organisationName;
            vm.resourceMeteData.organisationId = obj.organisationId;
            vm.resourceMeteData.organisationAddress = obj.organisationAddr;
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
        filterNode:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 树结构目录获取
        getMenuList: function (event) {
            $.getJSON(baseURL + "xj/xjmetesetcategory/list", function(r){
                console.log(r);
                r.forEach(function(item,i){
                    vm.fenlSelect.push({
                        name:item.name,
                        id:item.meteCategorySetId,
                        list:[]
                    })
                })

                var _list = [{
                    name:'元数据分类',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.fenlSelect;
                vm.menuList = _list;
                console.log(vm.menuList);
            });
        },
        // 获取表格列表
        getTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadataset/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
                    meteCategorySetId:this.q.meteCategorySetId,
                    meteSetNumber:this.q.meteSetNumber,
                    cnName:this.q.cnName,
                    reviewState:this.tab
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.tableList = r.page.list;
                        vm.totalPage = r.page.totalCount;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        // 分页
        layerPage:function (currentPage) {
            console.log(currentPage);
            vm.page = currentPage;
            vm.getTableList();
        },
        // 编辑分页
        layerPage1:function (currentPage) {
            console.log(currentPage);
            vm.page1 = currentPage;
            vm.getFileTableList();
        },
        // 树目录点击事件
        handleNodeClick:function(data) {
            console.log(data);
            console.log(JSON.stringify(data.id) == 'null');

            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                console.log('进来了')
                vm.meteCategorySetId = data.id;
                vm.q.meteCategorySetId = data.id;
                vm.getTableList();
            }

        },
        // 选项卡
        tabClick:function (num) {
            vm.tab = num;
            vm.page = 1;
            vm.getTableList();
        },
        // 表格选中方法
        toggleSelection:function(selection) {
            console.log(selection);
            vm.checkIdList = selection;
        },
        // 提交
        subMit:function () {
            var list = []
            vm.checkIdList.forEach(function (item) {
                list.push(item.meteSetId)
            })
            console.log(list);
            if(list.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else {
                $.ajax({
                    type: "post",
                    url: baseURL + 'xj/xjmetesetaudit/submit',
                    contentType: "application/json",
                    // dataType: 'json',
                    data: JSON.stringify(list),
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 1;
                            vm.page = 1;
                            vm.getTableList();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            }

        },
        // 撤回
        revoke:function (id) {

            layer.confirm('确定要撤回该条记录？', function(index){
                $.ajax({
                    type: "get",
                    url: baseURL + 'xj/xjmetesetaudit/revoke',
                    // contentType: "application/json",
                    dataType: 'json',
                    data: {
                        meteSetId:id
                    },
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 0;
                            vm.page = 1;
                            vm.getTableList();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            });


        },
        // 通过方法
        agree:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetesetaudit/agree',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteSetId:id
                },
                // data:JSON.stringify([id]),
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.tab = 1;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 拒绝
        refuse:function (id) {
            layer.open({
                type: 1,
                title: '审核意见',
                content: $('#refuseDiv'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '260px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    $.ajax({
                        type: "post",
                        url: baseURL + 'xj/xjmetesetaudit/refuse',
                        contentType: "application/json",
                        // dataType: 'json',
                        data: JSON.stringify({
                            meteSetId:id,
                            auditOpinion:vm.auditOpinion
                        }),
                        success: function(r){
                            if(r.code === 0){
                                vm.tab = 3;
                                vm.page = 1;
                                vm.getTableList();
                                layer.close(index);
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }else{
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });

                },
                btn2:function () {
                    // layer.close(index);
                    vm.getTableList();
                }

            })

        },
        // 发布
        push:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetesetaudit/push',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteSetId:id
                },
                success: function(r){
                    if(r.code === 0){
                        vm.tab = 4;
                        vm.page = 1;
                        vm.getTableList();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        // 停止发布
        stopPush:function (id) {
            layer.confirm('确定要停止发布该条记录？', function(index){
                $.ajax({
                    type: "get",
                    url: baseURL + 'xj/xjmetesetaudit/stopPush',
                    // contentType: "application/json",
                    dataType: 'json',
                    data: {
                        meteSetId:id
                    },
                    success: function(r){
                        if(r.code === 0){
                            vm.tab = 2;
                            vm.page = 1;
                            vm.getTableList();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                        }
                    }
                });
            })

        },
        // 获取审核记录
        getoptionList:function (id) {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetesetaudit/list',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    meteSetId:id
                },
                success: function(r){
                    console.log(r)
                    vm.optionList = r;
                    layer.open({
                        type: 1,
                        title: '审核记录',
                        content: $('#shenheList'), //这里content是一个普通的String
                        skin: 'openClass',
                        area: ['562px', '460px'],
                        shadeClose: true,
                        closeBtn:0,
                        btn: ['关闭'],
                        btn1:function (index) {

                            layer.close(index);
                        }

                    })
                }
            });
        },

        // 编辑方法
        query1: function () {
            vm.getTableList1();
        },
        clean1:function () {
            vm.q1 = {
                name:'',
                meteNumber:'',
                meteCategoryId:'',
                cnName:''
            }
        },
        // 收缩展开搜索
        openSwitch1:function () {
            if(vm.open1){
                vm.open1 = false;
                vm.openText1 = '收起筛选'

            }else {
                vm.open1 = true;
                vm.openText1 = '展开筛选'
            }
        },
        filterNode1:function(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        // 树结构目录获取元数据分类列表
        getMenuList1: function (event) {
            $.getJSON(baseURL + "xj/xjmetecategory/list", function(r){

                r.forEach(function(item,i){
                    vm.fenlSelect1.push({
                        name:item.name,
                        id:item.meteCategoryId,
                        list:[]
                    })
                })

                var _list = [{
                    name:'元数据分类',
                    id:null,
                    list:[]
                }]
                _list[0].list = vm.fenlSelect1;
                vm.menuList1 = _list;
                console.log(vm.menuList1);
            });
        },
        // 树目录点击事件
        handleNodeClick1:function(data) {
            console.log(data);
            console.log(JSON.stringify(data.id) == 'null');

            if(data.list.length == 0 || JSON.stringify(data.id) == 'null'){
                console.log('进来了')
                vm.q.meteCategoryId = data.id;
                vm.getTableList1();
            }

        },
        // 获取元数据列表
        getTableList1:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmetadata/queryList',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page1,
                    meteNumber:this.q1.meteNumber,
                    meteCategoryId:this.q1.meteCategoryId,
                    cnName:this.q1.cnName
                },
                success: function(r){
                    console.log(r);
                    if(r.code === 0){
                        vm.tableList1 = r.page.list;
                        vm.totalPage1 = r.page.totalCount;
                    }else{
                        alert(r.msg);
                    }
                }
            });
        },
        addUp:function () {
            vm.getMenuList1();
            vm.getTableList1();
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['1000px', '660px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    if(JSON.stringify(vm.resourceMeteData.meteDataList) != 'null'){
                        console.log('jin')
                        vm.checkIdList2.forEach(function (item,i) {
                            vm.resourceMeteData.meteDataList.push(item);
                        })
                    }else {
                        vm.resourceMeteData.meteDataList = vm.checkIdList2;
                    }
                    // vm.resourceMeteData.meteDataList = vm.checkIdList2;
                    console.log(vm.resourceMeteData)
                    layer.close(index);
                },
                btn2:function () {

                }

            })
        },
        delUp:function () {
            var arr =[];
            vm.checkIdList1.forEach(function (item,i) {
                vm.resourceMeteData.meteDataList.forEach(function (m,n) {
                    if(m.meteId == item.meteId){
                        arr.push(n);
                        return
                    }
                })
            })
            console.log(arr);
            arr.forEach(function (item) {
                vm.resourceMeteData.meteDataList.splice(item,1)
            })

        },
        // 导出
        outUp:function () {
            window.location.href = baseURL + "xj/xjmetadataset/downField/"+vm.id
        },
        // 表格选中方法
        toggleSelection1:function(selection) {
            console.log(selection);
            vm.checkIdList1 = selection;
        },
        // 表格选中方法
        toggleSelection2:function(selection) {
            console.log(selection);
            vm.checkIdList2 = selection;
        },
        // 获取历史版本
        getHist:function (id) {
            var _this = this;
            $.get(baseURL + "xj/xjmetadataset/historyInfo/"+id, function(r){
                console.log(r);
                if(r.hList.length == 0){
                    _this.$message({
                        message: '暂无历史版本',
                        type: 'warning'
                    });
                }else {
                    vm.hisList = r.hList;
                    layer.open({
                        type: 1,
                        title: '新增',
                        content: $('#hisList'), //这里content是一个普通的String
                        skin: 'openClass',
                        area: ['1000px', '580px'],
                        shadeClose: true,
                        closeBtn:0,
                        btn: ['关闭'],
                        btn1:function (index) {
                            layer.close(index);
                        },
                        btn2:function () {
                        }

                    })
                }

                // vm.tableListUp = r.resourceMeteData.list;
            });

        },
    },
    created:function () {
        this.getMenuList();
        this.getTableList();

        // this.h = height
    }
});


