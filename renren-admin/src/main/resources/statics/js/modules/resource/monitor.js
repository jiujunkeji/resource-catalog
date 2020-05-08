$(function () {
    var _height = $('.divBox').eq(0).find('.switchIn').height();
    var height = _height + 45 + 70;
    vm.h = height;
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "meteCategoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url:"nourl"
        },

    }
};
var ztree;

var vm = new Vue({
	el:'#rrapp',
	data:{
        q: {
            name:'',
            type:''
        },
		showList: true,
		title: null,
		meteCategory: {

		},
        tableList:[],
        totalPage:0,
        page:1,
        checkIdList:[],
        open:true,
        openText:'展开筛选',
        h:0,
        comList:[],
        restaurants: [],
        showList:true,
        resourceMeteData:{},
        loading:true
	},
	methods: {
		query: function () {
			vm.reload();
		},
        clean:function () {
            vm.q.name = null
        },
		del: function (event) {
			// var meteCategoryIds = getMeteCategoryId();
            if(vm.checkIdList.length == 0){
                this.$message({
                    message: '请选择一条记录',
                    type: 'warning'
                });
            }else{
                layer.confirm('确定要删除选中的记录？', function(index1){
                    $.ajax({
                        type: "POST",
                        url: baseURL + "xj/xjdatasource/delete",
                        contentType: "application/json",
                        data: JSON.stringify(vm.checkIdList),
                        success: function(r){
                            if(r.code == 0){
                                vm.reload();
                                layer.close(index1);
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/success.png"><br>操作成功</div>',{skin:'bg-class',area: ['400px', '270px']});


                            }else{
                                layer.msg('<div class="okDiv"><img src="'+baseURL+'statics/img/fail.png"><br>操作失败</div>',{skin:'bg-class',area: ['400px', '270px']});
                            }
                        }
                    });
                });
            }


		},
        // 获取表格列表
        getTableList:function () {
            this.loading = true;
            $.ajax({
                type: "get",
                url: baseURL + 'xj/xjmonitor/list',
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
                        vm.loading = false;
                    }else{
                        alert(r.msg);
                        vm.loading = false;
                    }
                }
            });
        },
        // 分页
        layerPage:function (currentPage) {
            vm.page = currentPage;
            vm.getTableList();
        },
        openToggleRowExpansion:function (row) {
            console.log(row);
        },
        lookC:function(row){
		    vm.showList = false;
            vm.resourceMeteData = row;
        },
        reload:function(){
            vm.showList = true;
        }
	},
	created:function () {
	    this.getTableList();
        // $.get(baseURL + "resource/metecategory/list", function(r){
        // });
    },
});


// var Menu = {
//     id: "menuTable",
//     table: null,
//     layerIndex: -1
// };
/**
 * 初始化表格的列
 */
// Menu.initColumn = function () {
//     var columns = [
//         {field: 'selectItem', radio: true},
//         {title: '分类名称', field: 'name', visible: false, align: 'center', valign: 'middle', width: '260px'},
//         {title: '分类类型', field: 'categoryType', align: 'center', valign: 'middle', sortable: true, width: '180px'},
//         {title: '描述', field: 'remark', align: 'center', valign: 'middle', sortable: true, width: ''},
//         {title: '分类代码', field: 'code', align: 'center', valign: 'middle', sortable: true, width: '100px',}]
//     return columns;
// };


function getMeteCategoryId () {
    var selected = $('#menuTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    // var colunms = Menu.initColumn();
    // var table = new TreeTable(Menu.id, baseURL + "resource/metecategory/list", colunms);
    // table.setExpandColumn(1);
    // table.setIdField("meteCategoryId");
    // table.setCodeField("meteCategoryId");
    // table.setParentCodeField("parentId");
    // table.setExpandAll(false);
    // table.init();
    // Menu.table = table;

})