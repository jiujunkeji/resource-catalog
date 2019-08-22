$(function () {
    // $("#jqGrid").jqGrid({
    //     url: baseURL + 'resource/organisationinfo/list',
    //     datatype: "json",
    //     colModel: [
		// 	{ label: 'organisationId', name: 'organisationId', index: 'organisation_id', width: 50, key: true },
		// 	{ label: '机构名称', name: 'organisationName', index: 'organisation_name', width: 80 },
		// 	{ label: '机构地址', name: 'organisationAddr', index: 'organisation_addr', width: 80 },
		// 	{ label: '联系人', name: 'linkman', index: 'linkman', width: 80 },
		// 	{ label: '联系电话', name: 'phone', index: 'phone', width: 80 }
    //     ],
		// viewrecords: true,
    //     height: 385,
    //     rowNum: 10,
		// rowList : [10,30,50],
    //     rownumbers: false,
    //     rownumWidth: 25,
    //     autowidth:true,
    //     multiselect: true,
    //     pager: "#jqGridPager",
    //     jsonReader : {
    //         root: "page.list",
    //         page: "page.currPage",
    //         total: "page.totalPage",
    //         records: "page.totalCount"
    //     },
    //     prmNames : {
    //         page:"page",
    //         rows:"limit",
    //         order: "order"
    //     },
    //     gridComplete:function(){
    //     	//隐藏grid底部滚动条
    //     	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    //     }
    // });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		organisationInfo: {},
        tableList:[],
        totalPage:0,
        checkIdList:[],
        page:1,

	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.organisationInfo = {};
            layer.open({
                type: 1,
                title: '新增',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['600px', '520px'],
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
		},
		update: function (id) {
			var organisationId = id;
			if(organisationId == null){
				return ;
			}
            layer.open({
                type: 1,
                title: '修改',
                content: $('#addUp'), //这里content是一个普通的String
                skin: 'openClass',
                area: ['600px', '520px'],
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
            vm.title = "修改";
            
            vm.getInfo(organisationId)
		},
		saveOrUpdate: function (event) {
			var url = vm.organisationInfo.organisationId == null ? "resource/organisationinfo/save" : "resource/organisationinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.organisationInfo),
			    success: function(r){
			    	if(r.code === 0){
						vm.getTableList();
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
					}else{
                        layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
					}
				}
			});
		},
		del: function (event) {
            var organisationIds = [];
            vm.checkIdList.forEach(function (item) {
                organisationIds.push(item.organisationId);
            })
			// var organisationIds = getSelectedRows();
			if(organisationIds == null){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
			}else {
                confirm('确定要删除选中的记录？', function(){
                    $.ajax({
                        type: "POST",
                        url: baseURL + "resource/organisationinfo/delete",
                        contentType: "application/json",
                        data: JSON.stringify(organisationIds),
                        success: function(r){
                            if(r.code == 0){
                                vm.tab = 1;
                                vm.page = 1;
                                vm.getTableList();
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }else{
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>'+r.msg+'</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                });
			}
			

		},
		getInfo: function(organisationId){
			$.get(baseURL + "resource/organisationinfo/info/"+organisationId, function(r){
                vm.organisationInfo = r.organisationInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			vm.getTableList();
            // var page = $("#jqGrid").jqGrid('getGridParam','page');
            // $("#jqGrid").jqGrid('setGridParam',{
            //     page:page
            // }).trigger("reloadGrid");
		},
        // 表格选中方法
        toggleSelection:function(selection) {
            console.log(selection);
            vm.checkIdList = selection;
        },
		// 获取表格列表
        getTableList:function () {
            $.ajax({
                type: "get",
                url: baseURL + 'resource/organisationinfo/list',
                // contentType: "application/json",
                dataType: 'json',
                data: {
                    page:this.page,
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
	},
	created:function () {
		this.getTableList();
    }
});