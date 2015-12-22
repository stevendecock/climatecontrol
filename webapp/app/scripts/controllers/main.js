'use strict';

/**
 * @ngdoc function
 * @name climatecontrolApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the climatecontrolApp
 */
angular.module('climatecontrolApp')
  .controller('MainCtrl', function () {

    $(function () {
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
            min: 0
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
            text: 'Vochtigheid'
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

        $.getJSON('http://localhost:8080/data/basement/temperature?callback=?', function (data) {
        		var chart = $('#temperatuur').highcharts();
            chart.series[0].setData(data);
        });
        $.getJSON('http://localhost:8080/data/outside/temperature?callback=?', function (data) {
        		var chart = $('#temperatuur').highcharts();
            chart.series[1].setData(data);
        });
        $.getJSON('http://localhost:8080/data/basement/humidity?callback=?', function (data) {
        		var chart = $('#vochtigheid').highcharts();
            chart.series[0].setData(data);
        });
        $.getJSON('http://localhost:8080/data/outside/humidity?callback=?', function (data) {
        		var chart = $('#vochtigheid').highcharts();
            chart.series[1].setData(data);
        });

    });


  });
