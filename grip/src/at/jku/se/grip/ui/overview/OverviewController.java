package at.jku.se.grip.ui.overview;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.PointLabels;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.directions.BarDirections;
import org.dussan.vaadin.dcharts.metadata.locations.LegendLocations;
import org.dussan.vaadin.dcharts.metadata.locations.PointLabelLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.BarRenderer;

import com.google.common.collect.Lists;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.Component;

import at.jku.se.grip.beans.Drawing;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.dao.DaoServiceRegistry;
import lombok.val;

public class OverviewController {
	
	private OverviewView view = null;
	DCharts chart = null;
	
	public OverviewController(){
		view = new OverviewView();
		init();
	}
	
	private void init() {
		Component c = createChartSlot();
		c.addAttachListener(this::attachListener);
		
		view.getDashboardPanelsCssLayout().addComponent(createChartSlot());
	}
	
	private void attachListener(AttachEvent e) {
		chart.replot(true, true);
	}
	
	public OverviewView getView(){
		return view;
	}
	
//	public saveNote() {
//		
//	}
	
	private Component createChartSlot() {
		List<Drawing> beans = DaoServiceRegistry.getDrawingDAO().findByCriteria(
				CriteriaFactory.create()
				.descOrder("header.modifiedDate")
				.descOrder("header.createdDate"));
		DataSeries dataSeries = new DataSeries();
		Series series = new Series();

		// iterate over collection and generate chart data and legend
		int i = 0;
		beans = Lists.reverse(beans);
		i = 0;
		for(val bean : beans) {
			if(i < 10) {
				dataSeries.newSeries().add(bean.getPathLength()/100, "");
				series.addSeries((new XYseries().setLabel(bean.getName())));
			} else {
				break;
			}
			i++;
		}
		
		SeriesDefaults seriesDefaults = new SeriesDefaults()
			.setRenderer(SeriesRenderers.BAR)
			.setLineWidth(2)
			.setPointLabels(
				new PointLabels()
					.setShow(true)
					.setLocation(PointLabelLocations.EAST)
					.setEdgeTolerance(-15))
			.setShadowAngle(135)
			.setRendererOptions(
				new BarRenderer()
					.setBarDirection(BarDirections.HOTIZONTAL));
		
		Legend legend = new Legend()
				.setShow(true)
				.setPlacement(LegendPlacements.OUTSIDE_GRID)
				.setLocation(LegendLocations.EAST);

		Axes axes = new Axes()
			.addAxis(
				new XYaxis(XYaxes.Y)
					.setRenderer(AxisRenderers.CATEGORY));

		Options options = new Options()
			.setSeriesDefaults(seriesDefaults)
			.setSeries(series)
			.setLegend(legend)
			.setAxes(axes)
			.setAnimate(true)
			.setAnimateReplot(true);

		chart = new DCharts()
			.setDataSeries(dataSeries)
			.setOptions(options)
			.show();
		chart.setWidth("600px");
		chart.setHeight("300px");
		chart.setCaption("Last Path Lengths");

		return view.createContentWrapper(chart);
	}
	
	private void init2(){
		/*DataSeries dataSeries = new DataSeries()
				.add(2, 6, 7, 10);

		SeriesDefaults seriesDefaults = new SeriesDefaults()
				.setRenderer(SeriesRenderers.BAR)
				.setRendererOptions(
						new BarRenderer()
							.setBarDirection(BarDirections.HOTIZONTAL));

		Axes axes = new Axes()
				.addAxis(
						new XYaxis()
						.setRenderer(AxisRenderers.CATEGORY)
						.setTicks(
								new Ticks()
								.add("10.06.", "09.06", "08.06", "30.05.")));

		Highlighter highlighter = new Highlighter()
				.setShow(false);

		Options options = new Options()
				.setSeriesDefaults(seriesDefaults)
				.setAxes(axes)
				.setHighlighter(highlighter);

		DCharts chart = new DCharts()
				.setDataSeries(dataSeries)
				.setOptions(options)
				.show();
		getView().getCanvasLayout().addComponent(chart);
		*/
		
		// generate test chart
		/*DataSeries dataSeries = new DataSeries();
		dataSeries.newSeries()
			.add(2, 1)
			.add(4, 2)
			.add(6, 3)
			.add(3, 4);
		dataSeries.newSeries()
			.add(5, 1)
			.add(1, 2)
			.add(3, 3)
			.add(4, 4);
		dataSeries.newSeries()
			.add(4, 1)
			.add(7, 2)
			.add(1, 3)
			.add(2, 4);
			*/

		DataSeries dataSeries = new DataSeries();
		dataSeries.newSeries()
//			.add(2, 1)
//			.add(4, 2)
//			.add(6, "09.06")
			.add(3, "10.06");
		dataSeries.newSeries()
//			.add(5, 1)
//			.add(1, 2)
//			.add(3, "09.06")
			.add(4, "11.06");
		dataSeries.newSeries()
//			.add(4, 1)
//			.add(7, 2)
//			.add(1, "09.06")
			.add(2, "12.06");
		
		SeriesDefaults seriesDefaults = new SeriesDefaults()
			.setRenderer(SeriesRenderers.BAR)
			.setPointLabels(
				new PointLabels()
					.setShow(true)
					.setLocation(PointLabelLocations.EAST)
					.setEdgeTolerance(-15))
			.setShadowAngle(135)
			.setRendererOptions(
				new BarRenderer()
					.setBarDirection(BarDirections.HOTIZONTAL));

		Axes axes = new Axes()
			.addAxis(
				new XYaxis(XYaxes.Y)
					.setRenderer(AxisRenderers.CATEGORY));

		Options options = new Options()
			.setSeriesDefaults(seriesDefaults)
			.setAxes(axes);

		DCharts chart = new DCharts()
			.setDataSeries(dataSeries)
			.setOptions(options)
			.show();
		view.getDashboardPanelsCssLayout().addComponent(view.createContentWrapper(chart));
//		getView().getCanvasLayout().addComponent(chart);
		
		/*------------------------------------*/
		
//		BrowserFrame frame = new BrowserFrame();
//		frame.setWidth("1000px");
//		frame.setHeight("600px");
		//frame.setSource(new StreamResource(new ChartStreamSource(), "chart"));
		//getView().getCanvasLayout().addComponent(frame);

	}
	
	private static class ChartStreamSource implements StreamSource {

		//private static final byte[] HTML = "<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization.arrayToDataTable([['Age', 'Weight'],[ 8,      12],[ 4,      5.5],[ 11,     14],[ 4,      5],[ 3,      3.5],[ 6.5,    7]]);var options = {title: 'Age vs. Weight comparison',hAxis: {title: 'Age', minValue: 0, maxValue: 15},vAxis: {title: 'Weight', minValue: 0, maxValue: 15},legend: 'none'};var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));chart.draw(data, options);}</script></head><body><div id=\"chart_div\" style=\"width: 200px; height: 200px;\"></div></body></html>".getBytes();
		private static final byte[] HTML = "<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization.arrayToDataTable([['Age', 'Weight'],[ 8,      12],[ 4,      5.5],[ 11,     14],[ 4,      5],[ 3,      3.5],[ 6.5,    7]]);var options = {title: 'Age vs. Weight comparison', width: 300, height: 300, hAxis: {title: 'Age', minValue: 0, maxValue: 15},vAxis: {title: 'Weight', minValue: 0, maxValue: 15},legend: 'none'};var chart = new google.visualization.ScatterChart(document.getElementById('chartFrame'));chart.draw(data, options);}</script></head></html>".getBytes();

		public InputStream getStream() {
			return new ByteArrayInputStream(HTML);
		}

	}
	
}
