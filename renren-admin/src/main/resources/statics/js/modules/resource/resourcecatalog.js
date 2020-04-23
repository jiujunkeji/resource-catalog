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

var vm = new Vue({
	el:'#rrapp',
	data:{
		q: {
            name:''
		},
		showList: true,
		title: null,
		resourceCatalog: {
            parentId:null,
            parentName:''
        },
        gatntObj: {
            deptId:null,
            userId:null,
        },
        imageUrl:'',
        fileData:null,
        name:null,
        open:true,
        openText:'展开筛选',
        h:0,
        dept:[
            {
                deptId:1,
                deptName:'11',
                childrenList1:null
            }
        ],
        user:[],
        dept:[],
        // props: {
		 //    multiple: true,
        //     value:'deptId',
        //     label:'deptName',
        //     children:'childrenList'
        // },
        propsOut: {
            label: 'name',
            children: 'list',
        },
        menuListOut:[],
        menuList:[],
        props: {
            label: 'name',
            children: 'list',
        },
        // catalogList:
	},
	methods: {
		query: function () {
			vm.reload();
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
                var node = ztree.getNodeByParam("catalogId", vm.resourceCatalog.parentId);
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
                    vm.resourceCatalog.parentId = node[0].catalogId;
                    vm.resourceCatalog.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
		add: function(){
		    layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['新增','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate();
                    layer.close(index);
                },
                btn2:function () {
                    vm.reload();
                }

            })
			vm.showList = false;
			vm.title = "新增";
			vm.resourceCatalog = {
                parentId:null,
                parentName:''
            };
            vm.getMenu();
		},
		update: function (id) {
			var catalogId = id;
			if(catalogId == null){
				return ;
			}
            layer.open({
                type: 1,
                title: '修改',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['修改','取消'],
                btn1:function (index) {
                    vm.saveOrUpdate();
                    layer.close(index);

                },
                btn2:function () {
                    vm.reload();
                }

            })
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(catalogId);
            vm.getMenu();
		},
		saveOrUpdate: function (event) {
            if(vm.validator()){
                return ;
            }
			var url = vm.resourceCatalog.catalogId == null ? "resource/resourcecatalog/save" : "resource/resourcecatalog/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.resourceCatalog),
			    success: function(r){
			    	if(r.code === 0){
                        vm.reload();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
					}else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
					}
				}
			});
		},
		del: function (id) {
			var catalogIds = id;
            console.log(catalogIds);
			if(catalogIds == null){
				return ;
			}
			
			layer.confirm('确定要删除选中的记录？', function(index){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/resourcecatalog/delete",
                    contentType: "application/json",
				    data: JSON.stringify(catalogIds),
				    success: function(r){
						if(r.code == 0){
                            layer.close(index);
                            vm.reload();
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});

						}else{
                            layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
						}
					}
				});
			});
		},
        clean:function () {
            vm.q.name = null
        },
		getInfo: function(catalogId){
            $.get(baseURL + "resource/resourcecatalog/info/"+catalogId, function(r){
                vm.resourceCatalog = r.resourceCatalog;
                console.log('修改');
                console.log(vm.resourceCatalog);
            });
		},
		reload: function (event) {
			vm.showList = true;
            Menu.table.setData(vm.q);
            Menu.table.refresh();
		},
        validator: function () {
            if(isBlank(vm.resourceCatalog.name)){
                alert("目录名称不能为空");
                return true;
            }
        },
		downTemplate: function () {
			window.location.href = baseURL + "resource/resourcecatalog/downTemplate"
        },
		// 导出目录
        downCatalog: function () {
            window.location.href = baseURL + "resource/resourcecatalog/downCatalog"
        },
		// 导入
		daoru:function () {

        },
        // 导入完成
        handleAvatarSuccess:function(res, file) {
            // vm.imageUrl = URL.createObjectURL(file.raw);
            // vm.file = file;
            console.log(res);
            if(res.code == 0){
                this.$message({
                    type: 'success',
                    message: '导入成功！'
                });
                vm.reload();
            }
        },
        // 导入前
        beforeAvatarUpload:function(file) {
            var FileExt = file.name.replace(/.+\./, "");
            if (['xlsx','xls'].indexOf(FileExt.toLowerCase()) === -1){
                this.$message({
                    type: 'warning',
                    message: '上传文件只能是excel！'
                });
                return false;
            }else {
            	file.type = 'xls';
            	vm.fileData = file;
            	console.log(vm.fileData);
			}


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
        // 机构
        getDept:function () {
            $.get(baseURL + "resource/organisationinfo/selectList", function(r){
                console.log(r);
                vm.dept = r;
            })
        },
        deptChange:function (obj) {
            console.log(obj);
            vm.gatntObj.deptId = obj;
            var treeF = this.$refs.tree1;
            this.$refs.tree1.setCheckedKeys([]);
            $.ajax({
                type: "GET",
                url: baseURL + "resource/resourcecatalog/selectOgGrant",
                data: {
                    organisationId:vm.gatntObj.deptId
                },
                success: function(r){
                    if(r.code == 0){
                        console.log(r);
                        // vm.gatntObj.deptId = r.deptList;
                        r.catalogIdList.forEach(function (item) {
                            treeF.setChecked(item,true,false);
                        })

                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
            // this.$refs.tree1.setCheckedKeys([]);
            // var idList = [1,5,7,8,9];


        },
        // 用户
        getUser:function () {
            $.get(baseURL + "sys/user/selectList", function(r){
                console.log(r);
                vm.user = r;
            })
        },
        userChange:function (obj) {
            console.log(obj);
            vm.gatntObj.userId = obj;
            var treeF = this.$refs.tree;
            this.$refs.tree.setCheckedKeys([]);
            $.ajax({
                type: "GET",
                url: baseURL + "resource/resourcecatalog/selectGrant",
                data: {
                    userId:vm.gatntObj.userId
                },
                success: function(r){
                    if(r.code == 0){
                        // vm.gatntObj.deptId = r.deptList;
                        r.catalogIdList.forEach(function (item) {
                            treeF.setChecked(item,true,false);
                        })

                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
            this.$refs.tree.setCheckedKeys([]);
            // var idList = [1,5,7,8,9];


        },
        // 授权
        setGrant:function (id) {
            var index = layer.load(2);
            $.ajax({
                type: "POST",
                url: baseURL + "resource/resourcecatalog/grant",
                contentType: "application/json",
                data: JSON.stringify({
                    userId:vm.gatntObj.userId,
                    catalogIdList:id
                }),
                success: function(r){
                    if(r.code == 0){
                        vm.reload();
                        layer.close(index);
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        getGrant:function () {
		    var treeF = this.$refs.tree;
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
                    console.log(treeF.getCheckedKeys());
                    console.log(treeF.getHalfCheckedKeys());
                    var allCheck = treeF.getHalfCheckedKeys().concat(treeF.getCheckedKeys());
                    console.log(allCheck);
                    vm.setGrant(allCheck);
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.userId = null;
                    layer.close(index);
                },
                btn2:function () {
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.userId = null;
                    vm.reload();

                }
            })

        },
        // 外部授权
        setOutGrant:function (id) {
            var index = layer.load(2);
            $.ajax({
                type: "POST",
                url: baseURL + "resource/resourcecatalog/grantOg",
                contentType: "application/json",
                data: JSON.stringify({
                    organisationId:vm.gatntObj.deptId,
                    catalogIdList:id
                }),
                success: function(r){
                    if(r.code == 0){
                        vm.reload();
                        layer.close(index);
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }else{
                        layer.close(index);
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                    }
                }
            });
        },
        getOutGrant:function () {
		    vm.menuListOut = vm.menuList;
            var treeF = this.$refs.tree1;
            layer.open({
                type: 1,
                title: '授权',
                content: $('#grantOut'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['562px', '520px'],
                shadeClose: true,
                closeBtn:0,
                btn: ['确定','取消'],
                btn1:function (index) {
                    console.log(treeF.getCheckedKeys());
                    console.log(treeF.getHalfCheckedKeys());
                    var allCheck = treeF.getHalfCheckedKeys().concat(treeF.getCheckedKeys());
                    console.log(allCheck);
                    vm.setOutGrant(allCheck);
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.deptId = null;
                    layer.close(index);
                },
                btn2:function () {
                    treeF.setCheckedKeys([]);
                    vm.gatntObj.userId = null;
                    vm.reload();

                }
            })

        },
        // 树结构目录获取
        getMenuList: function (event) {
            $.getJSON(baseURL + "resource/resourcecatalog/list", function(r){
                console.log(r);
                var _len=0;
                for(var i = 1;i<100;i++){
                    if(i == 1){
                        if(_len == r.length){
                            return ;
                        }
                        r.forEach(function (item) {
                            if(item.parentId == 0){
                                vm.menuList.push({
                                    name:item.name,
                                    id:item.catalogId,
                                    list:[]
                                })
                                _len++;
                            }
                        })
                    }else if(i == 2){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            r.forEach(function (n) {
                                if(n.parentId == item.id){
                                    item.list.push({
                                        name:n.name,
                                        id:n.catalogId,
                                        list:[]
                                    })
                                    _len++;
                                }
                            })
                        })
                    }else if(i == 3){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                r.forEach(function (n) {
                                    if(n.parentId == i.id){
                                        i.list.push({
                                            name:n.name,
                                            id:n.catalogId,
                                            list:[]
                                        })
                                    }
                                    _len++;
                                })
                            })

                        })
                    }else if(i == 4){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    r.forEach(function (n) {
                                        if(n.parentId == j.id){
                                            j.list.push({
                                                name:n.name,
                                                id:n.catalogId,
                                                list:[]
                                            })
                                        }
                                        _len++;
                                    })
                                })
                            })

                        })
                    }else if(i == 5){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        r.forEach(function (n) {
                                            if(n.parentId == m.id){
                                                m.list.push({
                                                    name:n.name,
                                                    id:n.catalogId,
                                                    list:[]
                                                })
                                            }
                                            _len++;
                                        })
                                    })
                                })
                            })

                        })
                    }else if(i == 6){
                        if(_len == r.length){
                            return ;
                        }
                        vm.menuList.forEach(function (item) {
                            item.list.forEach(function (i) {
                                i.list.forEach(function (j) {
                                    j.list.forEach(function (m) {
                                        m.list.forEach(function (x) {
                                            r.forEach(function (n) {
                                                if(n.parentId == x.id){
                                                    x.list.push({
                                                        name:n.name,
                                                        id:n.catalogId,
                                                        list:[]
                                                    })
                                                }
                                                _len++;
                                            })
                                        })
                                    })
                                })
                            })

                        })
                    }

                }


                console.log(vm.menuList);
            });
        },
	},
	created:function () {
        // this.h = height
        // this.getDept();
        this.getUser();
        this.getDept();
        this.getMenuList();
        // this.getUser();
    }
});

var Menu = {
    id: "menuTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        // {field: 'selectItem', radio: true},
        {title: '目录名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '260px'},
        // {title: '目录类型', field: 'type', align: 'center', valign: 'middle', sortable: true, width: '180px',formatter: function(item, index){
        //     if(item.type == 0){
        //         return '资源';
        //     }
        //     if(item.isUsed == 1){
        //         return '服务';
        //     }
        // }},
        {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: ''},
        {title: '修改时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: true, width: '200px',},
        {title: '使用情况', field: 'isUsed', align: 'center', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
        	if(item.isUsed == 0){
                return '<div style="margin-left: 6px" class="layui-unselect layui-form-switch" onClick="ss('+item.isUsed+','+item.catalogId+')"><em>停用</em><i></i></div>';
			}
			if(item.isUsed == 1){
                return '<div style="margin-left: 6px" class="layui-unselect layui-form-switch layui-form-onswitch" onClick="ss('+item.isUsed+','+item.catalogId+')"><em>使用</em><i></i></div>';
			}
        }},
        {title: '操作', field: '', align: 'left', valign: 'middle', sortable: true, width: '100px', formatter: function(item, index){
            return '<i class="el-icon-edit" style="margin-left: 10px" onclick="updateT('+item.catalogId+')"></i> <span style="color: #ccc;display: inline-block;margin: 0 6px">|</span> <i class="el-icon-delete" onclick="delT('+item.catalogId+')"></i>'
        }}]
    return columns;
};

function ss(num,id) {
	if(num == 0){
        layer.confirm('确定使用吗？',function (index1) {
            $.get(baseURL + "resource/resourcecatalog/start?catalogId="+id, function(r){
                Menu.table.refresh();
                layer.close(index1)
            });
		})

	}else {
        layer.confirm('确定停用吗？',function (index1) {
            $.get(baseURL + "resource/resourcecatalog/stop?catalogId="+id, function(r){
                Menu.table.refresh();
                layer.close(index1)
            });
        })
	}
}
function getCatalogId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}

function updateT(id) {
    vm.update(id);
}
function delT(id) {
    vm.del(id);
}


$(function () {
    var colunms = Menu.initColumn();
    var table = new TreeTable(Menu.id, baseURL + "resource/resourcecatalog/list", colunms);
    table.setExpandColumn(0);
    table.setIdField("catalogId");
    table.setCodeField("catalogId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.setData(vm.q);
    table.init();
    Menu.table = table;


});