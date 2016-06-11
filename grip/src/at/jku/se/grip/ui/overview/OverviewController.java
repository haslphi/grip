package at.jku.se.grip.ui.overview;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.PointLabels;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.directions.BarDirections;
import org.dussan.vaadin.dcharts.metadata.locations.PointLabelLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.BarRenderer;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.ui.BrowserFrame;

public class OverviewController {
	
	private OverviewView view = null;
	
	public OverviewController(){
		view = new OverviewView();
		init();
	}
	
	private void init(){
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
		//getView().getCanvasLayout().addComponent(chart);
		
		/*------------------------------------*/
		
		BrowserFrame frame = new BrowserFrame();
		frame.setWidth("1000px");
		frame.setHeight("600px");
		frame.setSource(new StreamResource(new ChartStreamSource(), "chart"));
		getView().getCanvasLayout().addComponent(frame);

	}
	
	private static class ChartStreamSource implements StreamSource {

		private static final byte[] HTML = "<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization.arrayToDataTable([['Age', 'Weight'],[ 8,      12],[ 4,      5.5],[ 11,     14],[ 4,      5],[ 3,      3.5],[ 6.5,    7]]);var options = {title: 'Age vs. Weight comparison',hAxis: {title: 'Age', minValue: 0, maxValue: 15},vAxis: {title: 'Weight', minValue: 0, maxValue: 15},legend: 'none'};var chart = new google.visualization.ScatterChart(document.getElementById('chart_div'));chart.draw(data, options);}</script></head><body><div id=\"chart_div\" style=\"width: 900px; height: 500px;\"></div></body></html>".getBytes();

		public InputStream getStream() {
			return new ByteArrayInputStream(HTML);
		}

	}
	
	public OverviewView getView(){
		return view;
	}

}
