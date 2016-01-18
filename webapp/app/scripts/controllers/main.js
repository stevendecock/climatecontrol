'use strict';

/**
 * @ngdoc function
 * @name climatecontrolApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the climatecontrolApp
 */
angular.module('climatecontrolApp')
  .controller('MainCtrl', ['$location', function ($location) {
    $(function () {
        var now = new Date();
        var dayInMillis = 24*60*60*1000;
        var twoDaysInMillis = 2 * dayInMillis;

        var to = now.getTime();
        var from = to - twoDaysInMillis;

        var searchParams = $location.search();
        if (searchParams.from) {
          from = now.getTime() + (searchParams.from * dayInMillis);
        }
        if (searchParams.to) {
          to = now.getTime() + (searchParams.to * dayInMillis);
        }

        Highcharts.setOptions({                                            // This is for all plots, change Date axis to local timezone
            global : {
                useUTC : false
            }
        });
        $('#temperatuur').highcharts({
          chart: {
            zoomType: 'x'
          },
          title: {
            text: 'Temperatuur'
          },
          subtitle: {
            text: document.ontouchstart === undefined ?
            'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
          },
          xAxis: {
            type: 'datetime'
          },
          yAxis: {
            title: {
              text: 'Temperatuur'
            },
            min: undefined,
            plotBands: [{
              from: -100,
              to: 0,
              color: 'cyan'
            }]
          },
          legend: {
            enabled: true
          },
          plotOptions: {
            area: {
              fillColor: {
                linearGradient: {
                  x1: 0,
                  y1: 0,
                  x2: 0,
                  y2: 1
                },
                stops: [
                  [0, Highcharts.getOptions().colors[0]],
                  [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
              },
              marker: {
                radius: 2
              },
              lineWidth: 1,
              states: {
                hover: {
                  lineWidth: 1
                }
              },
              threshold: null
            }
          },

          series: [{
            type: 'line',
            name: 'Kelder',
            color: 'red',
            data: []
          },
          {
            type: 'line',
            name: 'Buiten',
            color: 'blue',
            data: []
          }]
        });

        $('#vochtigheid').highcharts({
          chart: {
            zoomType: 'x'
          },
          title: {
            text: 'Luchtvochtigheid'
          },
          subtitle: {
            text: document.ontouchstart === undefined ?
            'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
          },
          xAxis: {
            type: 'datetime'
          },
          yAxis: {
            title: {
              text: 'Luchtvochtigheid'
            }
          },
          legend: {
            enabled: true
          },
          plotOptions: {
            area: {
              fillColor: {
                linearGradient: {
                  x1: 0,
                  y1: 0,
                  x2: 0,
                  y2: 1
                },
                stops: [
                  [0, Highcharts.getOptions().colors[0]],
                  [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
                ]
              },
              marker: {
                radius: 2
              },
              lineWidth: 1,
              states: {
                hover: {
                  lineWidth: 1
                }
              },
              threshold: null
            }
          },

          series: [{
            type: 'line',
            name: 'Kelder',
            color: 'red',
            data: []
          },
          {
            type: 'line',
            name: 'Buiten',
            color: 'blue',
            data: []
          }]
        });

        $.getJSON('http://walho87d.no-ip.org:9090/data/basement/temperature?callback=?&from=' + from + '&to=' + to, function (data) {
        		var chart = $('#temperatuur').highcharts();
            chart.series[0].setData(data);
        });
        $.getJSON('http://walho87d.no-ip.org:9090/data/outside/temperature?callback=?&from=' + from + '&to=' + to, function (data) {
        		var chart = $('#temperatuur').highcharts();
            chart.series[1].setData(data);
        });
        $.getJSON('http://walho87d.no-ip.org:9090/data/basement/humidity?callback=?&from=' + from + '&to=' + to, function (data) {
        		var chart = $('#vochtigheid').highcharts();
            chart.series[0].setData(data);
        });
        $.getJSON('http://walho87d.no-ip.org:9090/data/outside/humidity?callback=?&from=' + from + '&to=' + to, function (data) {
        		var chart = $('#vochtigheid').highcharts();
            chart.series[1].setData(data);
        });

    });


  }]);
