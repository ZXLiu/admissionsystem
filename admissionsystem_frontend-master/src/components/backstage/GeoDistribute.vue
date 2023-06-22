<template>
    <div id="back-stage-geo-distribute" class="clearfix">
        <empty-data v-if="chartDataAll.rows.length === 0 && chartDataShandong.rows.length === 0"></empty-data>
        <div v-else>
            <div id="geo-distribute-country">
                <ve-map :data="chartDataAll" width="400px" height="400px" :settings="mapSettings" :extend="countryChartExtend" ></ve-map>
            </div>
            <div id="geo-distribute-province">
                <ve-map :data="chartDataShandong" :settings="shandongSettings" :extend="shandongChartExtend"   width="400px" height="400px"></ve-map>
            </div>
            <div id="geo-distribute-province-col">
                <ve-histogram :data="chartDataShandong" :settings="lineSettings" height="300px" width="1000px"></ve-histogram>
            </div>
        </div>

    </div>
</template>

<script>
    import {request} from "../../network/request";
    import EmptyData from "./EmptyData"
    import china from "../../assets/json/china"
    import shandong from "../../assets/json/shandong.json"
    export default {
        name: "GeoDistribute",
        data() {
            return {
                chartDataAll: {
                    columns: ['省份', '人数'],
                    rows: []
                },
                chartDataShandong: {
                    columns: ['城市', '人数'],
                    rows: []
                },
                lineSettings: {
                    itemStyle:{
                        color: '#0984D9'
                    }
                },
                mapSettings: {
                    mapOrigin: china,
                    itemStyle:{
                        normal:{
                            areaColor: 'rgba(9,132,217,0.1)',
                            color: '#0984D9'
                        },
                        emphasis:{
                            areaColor: 'rgba(9,132,217,0.5)'
                        }
                    }
                },
                shandongSettings: {
                    mapOrigin: shandong,
                    position: 'province/shandong',
                    itemStyle:{
                        normal:{
                            areaColor: 'rgba(9,132,217,0.1)',
                            color: '#0984D9'
                        },
                        emphasis:{
                            areaColor: 'rgba(9,132,217,0.5)'
                        }
                    }

                },
                countryChartExtend: {
                    visualMap:{
                        show: false,
                        min: 0,
                        max: 6000,
                        calculable: true, //是否显示拖拽用的手柄（手柄能拖拽调整选中范围）。
                        inRange:{
                            color: ['#37B8FF', '#379be0', '#2b96df', '#369be1', '#369ae0', "#0984D9", '#096DBF', '#094F9D']
                        }
                    }
                },
                shandongChartExtend: {
                    visualMap:{
                        show: false,
                        min: 290,
                        max: 350,
                        calculable: true, //是否显示拖拽用的手柄（手柄能拖拽调整选中范围）。
                        inRange:{
                            color:['#37B8FF', '#379be0', '#2b96df', '#369be1', '#369ae0', "#0984D9", '#096DBF', '#094F9D']
                        }
                    }
                }
            }
        },
        methods: {
            loadAll() {
                request({
                    url: 'student/getDistribute'
                }).then(res => {
                    if (res.code === '000') {
                        this.chartDataAll.rows = [];
                        res.data.forEach(item => {
                            this.chartDataAll.rows.push({'省份': item.province, '人数': item.count})
                        });
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch(err => {
                    this.$message.error('系统错误')
                })
            },
            loadShandong() {
                request({
                    url: 'student/getDistributeInProvince',
                    params: {
                        province: '山东'
                    }
                }).then(res => {
                    if (res.code === '000') {
                        this.chartDataShandong.rows = [];
                        res.data.forEach(item => {
                            this.chartDataShandong.rows.push({'城市': item.city, '人数': item.count})
                        });
                    } else {
                        this.$message.error(res.message)
                    }
                }).catch(err => {
                    this.$message.error('系统错误')
                })

            }
        },
        created() {
            this.loadAll();
            this.loadShandong();
        },
        components: {
            EmptyData
        }
    }
</script>

<style scoped lang="less">

    #geo-distribute-country{
        padding: 80px;
        float: left;
        background-color: #fff;
        width: 550px;
        height: 550px;
        margin: 5px;
        border-radius: 10px;
    }

    #geo-distribute-province{
        padding: 80px;
        float: left;
        background-color: #fff;
        width: 550px;
        height: 550px;
        margin: 5px;
        border-radius: 10px;
    }

    #geo-distribute-province-col{
        padding: 80px;
        float: left;
        margin: 5px;
        background-color: #fff;
        width: 1110px;
        height: 400px;
    }
</style>