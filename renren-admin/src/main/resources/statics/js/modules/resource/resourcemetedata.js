$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'resource/resourcemetedata/list',
        datatype: "json",
        colModel: [			
			{ label: 'meteId', name: 'meteId', index: 'mete_id', width: 50, key: true },
			{ label: '元数据类型（资源类型、服务类型）', name: 'meteType', index: 'mete_type', width: 80 }, 			
			{ label: '信息资源类型（主题分类）', name: 'resourceCategory', index: 'resource_category', width: 80 }, 			
			{ label: '资源分类id', name: 'categoryId', index: 'category_id', width: 80 }, 			
			{ label: '资源分类编码', name: 'catagoryCode', index: 'catagory_code', width: 80 }, 			
			{ label: '资源分类', name: 'categoryName', index: 'category_name', width: 80 }, 			
			{ label: '目录id', name: 'catalogId', index: 'catalog_id', width: 80 }, 			
			{ label: '信息资源名称', name: 'resourceTitle', index: 'resource_title', width: 80 }, 			
			{ label: '信息资源摘要', name: 'resourceAbstract', index: 'resource_abstract', width: 80 }, 			
			{ label: '资源提供方id', name: 'organisationId', index: 'organisation_id', width: 80 }, 			
			{ label: '资源提供单位', name: 'organisationName', index: 'organisation_name', width: 80 }, 			
			{ label: '资源提供方地址', name: 'organisationAddress', index: 'organisation_address', width: 80 }, 			
			{ label: '关键字', name: 'keyword', index: 'keyword', width: 80 }, 			
			{ label: '信息资源标识', name: 'resourceSign', index: 'resource_sign', width: 80 }, 			
			{ label: '元数据标识', name: 'metedataIdentifier', index: 'metedata_identifier', width: 80 }, 			
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '审核状态（0：待审核；1：通过；2：未通过）', name: 'reviewState', index: 'review_state', width: 80 }, 			
			{ label: '审核人id', name: 'reviewUserId', index: 'review_user_id', width: 80 }, 			
			{ label: '审核人', name: 'reviewUserName', index: 'review_user_name', width: 80 }, 			
			{ label: '审核部门id', name: 'reviewDeptId', index: 'review_dept_id', width: 80 }, 			
			{ label: '审核部门', name: 'reviewDeptName', index: 'review_dept_name', width: 80 }, 			
			{ label: '审核时间', name: 'reviewTime', index: 'review_time', width: 80 }, 			
			{ label: '发布状态（0：未发布；1：发布）', name: 'pushState', index: 'push_state', width: 80 }, 			
			{ label: '发布人id', name: 'pushUserId', index: 'push_user_id', width: 80 }, 			
			{ label: '发布人', name: 'pushUserName', index: 'push_user_name', width: 80 }, 			
			{ label: '发布部门id', name: 'pushDeptId', index: 'push_dept_id', width: 80 }, 			
			{ label: '发布部门', name: 'pushDeptName', index: 'push_dept_name', width: 80 }, 			
			{ label: '发布时间', name: 'pushTime', index: 'push_time', width: 80 }, 			
			{ label: '是否删除', name: 'isDeleted', index: 'is_deleted', width: 80 }			
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		resourceMeteData: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.resourceMeteData = {};
		},
		update: function (event) {
			var meteId = getSelectedRow();
			if(meteId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(meteId)
		},
		saveOrUpdate: function (event) {
			var url = vm.resourceMeteData.meteId == null ? "resource/resourcemetedata/save" : "resource/resourcemetedata/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.resourceMeteData),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var meteIds = getSelectedRows();
			if(meteIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "resource/resourcemetedata/delete",
                    contentType: "application/json",
				    data: JSON.stringify(meteIds),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(meteId){
			$.get(baseURL + "resource/resourcemetedata/info/"+meteId, function(r){
                vm.resourceMeteData = r.resourceMeteData;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});