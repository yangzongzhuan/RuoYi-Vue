<template>
  <div id="app" style="margin: auto;background-color: #f9f9f9;padding: 15px;min-height: 700px">
    <div style="background-color: white;line-height: 70px;height: 70px;text-align: center">
      <el-autocomplete
        v-model="dataGrid.listQuery.keywords"
        :fetch-suggestions="querySearchAsync"
        placeholder="请输入公司名称/统一社会信用代码"
        clearable
        style="width: 610px;"
        class="filter-item"
        @input="handleInput"
        @select="handleSelect"
        @keyup.enter.native="handleFilter"
      >
        <el-button slot="append" icon="el-icon-search" @click="handleFilter" />
      </el-autocomplete>
    </div>
    <div style="clear: both;background-color: white;margin-top: 15px;min-height: 300px;padding:15px">
      <el-table
        :data="dataGrid.list"
        border
        :header-row-style="{height:'40px',fontSize: '13px'}"
        :header-cell-style="{padding:'0px'}"
        :row-style="{height:'40px',fontSize: '13px'}"
        :cell-style="{padding:'0px'}"
        style="width: 100%;"
      >
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column
          prop="base.entname"
          label="企业名称"
          width="240"
        >
          <template slot-scope="scope">
            <div
              v-html="(scope.row.base.entname_hlf === undefined
                  || scope.row.base.entname_hlf === null
                  || scope.row.base.entname_hlf === '')?scope.row.base.entname: scope.row.base.entname_hlf"
            />
          </template>
        </el-table-column>
        <el-table-column
          prop="base.uniscid"
          label="统一社会信用代码"
          width="190"
        />
        <el-table-column
          prop="base.entstatus_cn"
          label="企业经营状态"
          style="text-align: left"
        >
          <template slot-scope="scope">
            {{ scope.row.base.entstatus_cn }}
          </template>
        </el-table-column>
        <el-table-column
          prop="base.islist_cn"
          label="是否上市"
        />
        <el-table-column
          prop="enrelparCount"
          label="关联企业数量"
        />
        <el-table-column
          prop="listEnrelparCount"
          width="280"
          label="上市公司自行披露关联企业数量"
        />
        <el-table-column
          fixed="right"
          label="操作"
          width="100"
        >
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="viewDetailInfo(scope.row.base.entid)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination style="text-align: right;" :total="dataGrid.total" :page.sync="dataGrid.listQuery.page" :limit.sync="dataGrid.listQuery.limit" @pagination="getList" />
    </div>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default({
  name: "SupplyRelaList",
  components: { Pagination },
  data() {
    return {
      dataGrid: {
        list: [
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 3,
            "base": {
              "entid": "4A2345AB756F4CB1BBA1F74B784ACB814",
              "entname": "贵州读食食品有限公司",
              "entname_hlf": null,
              "uniscid": "91520102MAAJX8H613",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "01CE51C5C7BE4D7AAC88C23A5C1FE9A64",
              "entname": "许昌曼石装修装饰工程有限公司",
              "entname_hlf": null,
              "uniscid": "91411000MA47M7QPXU",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "03EF8AB7584346F89767087FDE29EA904",
              "entname": "隆化顺鹏农业开发有限公司",
              "entname_hlf": null,
              "uniscid": "91130825MA0E70235J",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 2,
            "base": {
              "entid": "0748A65BB8AC4763835A13E32FA5465E4",
              "entname": "吉林省嘉励荣廷建筑工程有限公司",
              "entname_hlf": null,
              "uniscid": "91220106MA171H8C6F",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "060ED54EB23F4DF68C74970BD9014D234",
              "entname": "成都市青白江区草枯芙服装经营部",
              "entname_hlf": null,
              "uniscid": "91510113MA654J559W",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "0401056B987D4D1D815F1F20B936DE2B4",
              "entname": "阿克苏市精顺倡机电有限公司",
              "entname_hlf": null,
              "uniscid": "91652901MA795B355P",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "062C4781D61B41F088E47828191BEB544",
              "entname": "成都菲希璐医疗器械经营部",
              "entname_hlf": null,
              "uniscid": "91510106MA65LUHG82",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "0DF6235B85844BF693212FB80A9C66364",
              "entname": "长沙市卫建医疗设备有限公司",
              "entname_hlf": null,
              "uniscid": "91430100MA7BX1Q917",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 1,
            "base": {
              "entid": "059CE783DB3C48808FC6B59C21A193C94",
              "entname": "陕西医维他生物科技有限公司",
              "entname_hlf": null,
              "uniscid": "91611105MA6TWXPK1Q",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          },
          {
            "listEnrelparCount": 0,
            "listEnrelpar": null,
            "enrelparCount": 4,
            "base": {
              "entid": "6A6F42F930654727A17C920405DF4AF54",
              "entname": "臻福珠宝（北京）有限公司",
              "entname_hlf": null,
              "uniscid": "91110101MA01UDLG1U",
              "entstatus": "1",
              "entstatus_cn": "在营（开业）",
              "islist": "0",
              "islist_cn": "否",
              "skcode": null
            },
            "enrelpar": null
          }
        ],
        listLoading: false,
        listQuery: {
          page: 1,
          limit: 10,
          keywords: undefined
        },
        total: 0
      }
    }
  },
  methods:{
    handleInput(val) {
    },
    handleSelect(item) {
    },
    handleFilter() {
    },
    querySearchAsync(queryString, cb) {
    },
    getList() {
    },
    viewDetailInfo(row) {
      this.$router.push({ path: "/supplier_graph/relaDetailGraph" });
    }
  }
})
</script>
<style scoped lang="scss">

</style>
