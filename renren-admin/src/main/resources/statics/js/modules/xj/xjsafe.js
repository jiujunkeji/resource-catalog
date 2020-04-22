$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'xj/xjsafe/list',
        datatype: "json",
        colModel: [			
			{ label: 'safeId', name: 'safeId', index: 'safe_id', width: 50, key: true },
			{ label: '目录id', name: 'catalogId', index: 'catalog_id', width: 80 }, 			
			{ label: '目录名称', name: 'catalogName', index: 'catalog_name', width: 80 }, 			
			{ label: '安全等级code', name: 'safeCode', index: 'safe_code', width: 80 }, 			
			{ label: '安全等级', name: 'safe', index: 'safe', width: 80 }, 			
			{ label: '加密code', name: 'encryptCode', index: 'encrypt_code', width: 80 }, 			
			{ label: '加密方式', name: 'encrypt', index: 'encrypt', width: 80 }, 			
			{ label: '备注', name: 'remark', index: 'remark', width: 80 }			
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
		xjSafe: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.xjSafe = {};
		},
		update: function (event) {
			var safeId = getSelectedRow();
			if(safeId == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(safeId)
		},
		saveOrUpdate: function (event) {
			var url = vm.xjSafe.safeId == null ? "xj/xjsafe/save" : "xj/xjsafe/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.xjSafe),
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
			var safeIds = getSelectedRows();
			if(safeIds == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "xj/xjsafe/delete",
                    contentType: "application/json",
				    data: JSON.stringify(safeIds),
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
		getInfo: function(safeId){
			$.get(baseURL + "xj/xjsafe/info/"+safeId, function(r){
                vm.xjSafe = r.xjSafe;
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