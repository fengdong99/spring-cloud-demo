<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/comm.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>index</title>
    <link rel="stylesheet" type="text/css" href="../../static/plugins/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="../../static/plugins/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="../static/plugins/easyui/demo/demo.css">
    <script type="text/javascript" src="../../static/plugins/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="../../static/plugins/easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
    <div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
        SCM管理系统
    </div>
    <div data-options="region:'west',split:true,title:'菜单栏'" style="width:150px;padding:10px;">
        <div class="easyui-accordion" data-options="fit:true,border:false">
            <div title="Title1" style="padding:10px;">
                content1
            </div>
            <div title="Title2" data-options="selected:true" style="padding:10px;">
                content2
            </div>
            <div title="Title3" style="padding:10px">
                content3
                content4
            </div>
        </div>
    </div>
    <div data-options="region:'east',split:true,collapsed:true,title:'East'" style="width:100px;padding:10px;">
        east region
    </div>
    <div data-options="region:'south',border:false" style="height:25px;background:#A9FACD;padding:2px;">
        south region
    </div>
    <div data-options="region:'center',title:''">
        <div  id="Tabs"  class="easyui-tabs"  data-options="fit:true,border:false,plain:true" >

            <div id="mainTitle"   title="main" data-options="fit:true,tools:'#p-tools'" style="padding:10px;background-color:#d9edf7 ">
                id:${id}
            </div>
            <div title="DataGrid" style="padding:5px">
                <table class="easyui-datagrid"
                       data-options="url:'../../static/datagrid_data1.json',method:'get',singleSelect:true,fit:true,fitColumns:true">
                    <thead>
                    <tr>
                        <th data-options="field:'itemid'" width="80">Item ID</th>
                        <th data-options="field:'productid'" width="100">Product ID</th>
                        <th data-options="field:'listprice',align:'right'" width="80">List Price</th>
                        <th data-options="field:'unitcost',align:'right'" width="80">Unit Cost</th>
                        <th data-options="field:'attr1'" width="150">Attribute</th>
                        <th data-options="field:'status',align:'center'" width="50">Status</th>
                    </tr>
                    </thead>
                </table>
            </div>

        </div>

    </div>
</body>
</html>