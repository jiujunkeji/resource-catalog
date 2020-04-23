$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xj/xjcatalog/list',
        datatype: "json",
        colModel: [			
			{ label: 'catalogId', name: 'catalogId', index: 'catalog_id', width: 50, key: true },
			{ label: '目录名称', name: 'catalogName', index: 'catalog_name', width: 80 }, 			
			{ label: '类目id', name: 'categoryId', index: 'category_id', width: 80 }, 			
			{ label: '类目编码', name: 'categoryCode', index: 'category_code', width: 80 }, 			
			{ label: '类目名称', name: 'categoryName', index: 'category_name', width: 80 }, 			
			{ label: '上级目录id', name: 'parentId', index: 'parent_id', width: 80 }, 			
			{ label: '信息资源名称', name: 'resourceTitle', index: 'resource_title', width: 80 }, 			
			{ label: '信息资源摘要', name: 'resourceAbstract', index: 'resource_abstract', width: 80 }, 			
			{ label: '资源提供方id', name: 'organisationId', index: 'organisation_id', width: 80 }, 			
			{ label: '资源提供单位', name: 'organisationName', index: 'organisation_name', width: 80 }, 			
			{ label: '资源提供方地址', name: 'organisationAddress', index: 'organisation_address', width: 80 }, 			
			{ label: '信息资源标识', name: 'resourceSign', index: 'resource_sign', width: 80 }, 			
			{ label: '元数据标识', name: 'metedataIdentifier', index: 'metedata_identifier', width: 80 }, 			
			{ label: '元数据集id', name: 'meteSetId', index: 'mete_set_id', width: 80 }, 			
			{ label: '创建时间', name: 'createTime', index: 'create_time', width: 80 }, 			
			{ label: '修改时间', name: 'updateTime', index: 'update_time', width: 80 }, 			
			{ label: '审核状态（0：待提交；1：待审核；2：通过；3：未通过）', name: 'reviewState', index: 'review_state', width: 80 }, 			
			{ label: '发布状态（0：未发布；1：发布）', name: 'pushState', index: 'push_state', width: 80 }, 			
			{ label: '是否删除（0：未删除；1：删除）', name: 'isDeleted', index: 'is_deleted', width: 80 }			
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
		xjCatalog: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.xjCatalog = {};
		},
		update: function (event) {
			var catalogId = getSelectedRow();
			if(catalogId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(catalogId)
		},
		saveOrUpdate: function (event) {
			var url = vm.xjCatalog.catalogId == null ? "xj/xjcatalog/save" : "xj/xjcatalog/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.xjCatalog),
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
			var catalogIds = getSelectedRows();
			if(catalogIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "xj/xjcatalog/delete",
                    contentType: "application/json",
				    data: JSON.stringify(catalogIds),
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
		getInfo: function(catalogId){
			$.get(baseURL + "xj/xjcatalog/info/"+catalogId, function(r){
                vm.xjCatalog = r.xjCatalog;
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