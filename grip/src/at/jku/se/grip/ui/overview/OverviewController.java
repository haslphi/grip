package at.jku.se.grip.ui.overview;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.PointLabels;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.directions.BarDirections;
import org.dussan.vaadin.dcharts.metadata.locations.PointLabelLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.series.BarRenderer;

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
		getView().getCanvasLayout().addComponent(chart);
	}
	
	public OverviewView getView(){
		return view;
	}

}
