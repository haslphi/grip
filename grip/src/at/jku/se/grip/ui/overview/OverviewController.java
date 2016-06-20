package at.jku.se.grip.ui.overview;

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
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Component;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.renderers.ClickableRenderer.RendererClickEvent;
import com.vaadin.ui.renderers.ImageRenderer;

import at.jku.se.grip.GripUI;
import at.jku.se.grip.beans.Drawing;
import at.jku.se.grip.beans.Drawing.DrawingPersistenceEvent;
import at.jku.se.grip.beans.Note;
import at.jku.se.grip.common.CriteriaFactory;
import at.jku.se.grip.dao.DaoServiceRegistry;
import at.jku.se.grip.ui.events.OpenDrawingEvent;
import lombok.val;

public class OverviewController {
	
	private OverviewView view = null;
	DCharts chart = null;
	private Note userNote = null;
	
	public OverviewController(){
		view = new OverviewView();
		init();
	}
	
	private void init() {
		// get the user notes
		userNote = DaoServiceRegistry.getNoteDAO().findByUserId(GripUI.getUser().getId().getId());
		if(userNote != null && userNote.getNotes() != null) {
			view.getNotesTextArea().setValue(userNote.getNotes());
		}
		
		// configure notes save menu item
		if(view.getNoteComponent().getTools() != null) {
			// add listener to save item
			view.getNoteComponent().getTools().getItems().get(1).setCommand(this::notesSaveSelectedListener);
		}
		
		// fill drawing grid and add listeners
		refreshDrawingGrid();
    	ImageRenderer renderer = (ImageRenderer) view.getDrawingGrid().getColumn("icon").getRenderer();
    	renderer.addClickListener(this::drawingIconClickEvent);
    	view.getDrawingGrid().addItemClickListener(this::drawingItemClickListener);
		
		// create the chart component and fill it with data
		Component c = createChartSlot();
		refreshDrawingChart();
		
		view.getDashboardPanelsCssLayout().addComponent(c);
		view.getDashboardPanelsCssLayout().addComponent(view.getDrawingComponent());
		
		// register to the eventbus
		GripUI.getEventBus().register(this);
	}
	
	private void notesSaveSelectedListener(MenuItem selectedItem) {
		saveNote();
		Notification notif = new Notification("Save done");
		notif.setPosition(Position.TOP_CENTER);
		notif.show(Page.getCurrent());
	}
	
	private void drawingItemClickListener(ItemClickEvent event) {
		if(event.isDoubleClick() && event.getItemId() != null) {
			GripUI.getEventBus().post(new OpenDrawingEvent((Drawing) event.getItemId()));
		}
	}
	
	private void drawingIconClickEvent(RendererClickEvent event) {
		Drawing bean = (Drawing)event.getItemId();
		if(bean != null && !bean.isNew()) {
			GripUI.getEventBus().post(new OpenDrawingEvent(bean));
		}
	}
	
	/**
	 * Listen to {@link DrawingPersistenceEvent}s of {@link Drawing} beans.
	 * 
	 * @param event
	 */
	@Subscribe
	public void listenDrawingPersistenceAction(DrawingPersistenceEvent event) {
		refreshDrawingGrid();
		refreshDrawingChart();
	}
	
	public OverviewView getView(){
		return view;
	}
	
	/**
	 * Save the user notes.
	 */
	public void saveNote() {
		userNote.setNotes(view.getNotesTextArea().getValue());
		DaoServiceRegistry.getNoteDAO().save(userNote);
	}
	
	/**
	 * Refresh the data in the drawing grid.
	 */
	private void refreshDrawingGrid() {
		List<Drawing> beans = DaoServiceRegistry.getDrawingDAO().findByCriteria(CriteriaFactory.create().ascOrder("name"));
    	view.getDrawingGrid().setContainerDataSource(new BeanItemContainer<>(
                Drawing.class, beans));
	}
	
	/**
	 * Refresh the data for the chart.
	 */
	private void refreshDrawingChart() {
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
		
		chart.setDataSeries(dataSeries)
			.setOptions(options)
			.show();
	}
	
	/**
	 * Create the slot component with the included {@link Drawing} chart.
	 * 
	 * @return the created component
	 */
	private Component createChartSlot() {
		chart = new DCharts()
			.show();
		chart.setWidth("600px");
		chart.setHeight("300px");
		chart.setCaption("Last Path Lengths");

		return view.createContentWrapper(chart);
	}
	
}
