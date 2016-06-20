package at.jku.se.grip.ui.overview;

import java.util.Iterator;
import java.util.Locale;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.beans.Drawing;
import at.jku.se.grip.beans.Header;
import lombok.Getter;

@SuppressWarnings("serial")
public class OverviewView extends Panel {

    private VerticalLayout rootVerticalLayout = null;
    private HorizontalLayout headerHorizontalLayout = null;
    private Label headerLabel = null;
    private CssLayout dashboardPanelsCssLayout = null;
    private SlotComponent noteComponent = null;
    private TextArea notesTextArea = null;
    private Component drawingComponent = null;
    private Grid drawingGrid = null;
	
	public OverviewView(){
		super();
		init();
	}
	
	private void init(){
		this.addStyleName(ValoTheme.PANEL_BORDERLESS);
		this.setSizeFull();
		this.setContent(getRootVerticalLayout());
		Responsive.makeResponsive(getRootVerticalLayout());
	}
	
	public VerticalLayout getRootVerticalLayout() {
		if(rootVerticalLayout == null) {
			rootVerticalLayout = new VerticalLayout();
			rootVerticalLayout.setSizeFull();
			rootVerticalLayout.setMargin(true);
			rootVerticalLayout.addStyleName("dashboard-view");
			rootVerticalLayout.addComponent(getHeaderHorizontalLayout());
			rootVerticalLayout.addComponent(getDashboardPanelsCssLayout());
			rootVerticalLayout.setExpandRatio(getDashboardPanelsCssLayout(), 1f);
		}
		return rootVerticalLayout;
	}
	
	public HorizontalLayout getHeaderHorizontalLayout() {
		if(headerHorizontalLayout == null) {
			headerHorizontalLayout = new HorizontalLayout();
			headerHorizontalLayout.addStyleName("viewheader");
			headerHorizontalLayout.setSpacing(true);
			headerHorizontalLayout.addComponent(getHeaderLabel());
		}
		return headerHorizontalLayout;
	}
	
	public Label getHeaderLabel() {
		if(headerLabel == null) {
			headerLabel = new Label("Overview");
			headerLabel.setSizeUndefined();
			headerLabel.addStyleName(ValoTheme.LABEL_H1);
			headerLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		}
		return headerLabel;
	}
	
	public CssLayout getDashboardPanelsCssLayout() {
		if(dashboardPanelsCssLayout == null) {
			dashboardPanelsCssLayout = new CssLayout();
			dashboardPanelsCssLayout.setSizeFull();
			dashboardPanelsCssLayout.addStyleName("dashboard-panels");
			Responsive.makeResponsive(dashboardPanelsCssLayout);
			
			dashboardPanelsCssLayout.addComponent(getNoteComponent());
		}
		return dashboardPanelsCssLayout;
	}
	
	public SlotComponent getNoteComponent() {
		if(noteComponent == null) {
	        noteComponent = createContentWrapper(getNotesTextArea(), FontAwesome.FLOPPY_O);
	        noteComponent.addStyleName("notes");
		}
		return noteComponent;
	}
	
	public TextArea getNotesTextArea() {
		if(notesTextArea == null) {
			notesTextArea = new TextArea("Notes");
			//notesTextArea.setValue("Remember to:\n� Zoom in and out in the Sales view\n� Filter the transactions and drag a set of them to the Reports tab\n� Create a new report\n� Change the schedule of the movie theater");
			notesTextArea.setSizeFull();
			notesTextArea.addStyleName(ValoTheme.TEXTAREA_BORDERLESS);
		}
		return notesTextArea;
	}
	
	public Component getDrawingComponent() {
		if(drawingComponent == null) {
			drawingComponent = createContentWrapper(getDrawingGrid());
		}
		return drawingComponent;
	}
	
	public Grid getDrawingGrid(){
		if(drawingGrid == null) {
			drawingGrid = new Grid("Drawing Quickselect");
			drawingGrid.setSizeFull();
			
			drawingGrid.setContainerDataSource(new BeanItemContainer<>(Drawing.class));
			drawingGrid.removeAllColumns();
			drawingGrid.addColumn("name").setExpandRatio(4).setHeaderCaption("Path Name");
			drawingGrid.addColumn("header").setExpandRatio(1).setHeaderCaption("User").setConverter(new Converter<String, Header>(){

				@Override
				public Header convertToModel(String value, Class<? extends Header> targetType, Locale locale)
						throws com.vaadin.data.util.converter.Converter.ConversionException {
					return null;
				}

				@Override
				public String convertToPresentation(Header value, Class<? extends String> targetType, Locale locale)
						throws com.vaadin.data.util.converter.Converter.ConversionException {
					return value.getCreatedBy().getUsername();
				}

				@Override
				public Class<Header> getModelType() {
					return Header.class;
				}

				@Override
				public Class<String> getPresentationType() {
					return String.class;
				}
				
			});
			drawingGrid.addColumn("icon").setHeaderCaption("").setWidth(62).setRenderer(new ImageRenderer());
			
			drawingGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
		}
		return drawingGrid;
	}
	
	protected SlotComponent createContentWrapper(final Component content, FontAwesome ...icons) {
        final SlotComponent slot = new SlotComponent();
        slot.setWidth("100%");
        slot.addStyleName("dashboard-panel-slot");

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");

        Label caption = new Label(content.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        content.setCaption(null);

        MenuBar tools = slot.getTools();
        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!slot.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(slot, true);
                } else {
                    slot.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(slot, false);
                }
            }
        });
        max.setStyleName("icon-only");
        
        // add dynamic menu items
        if(icons != null) {
	        for (FontAwesome icon : icons) {
	        	tools.addItem("", icon, null);
			}
        }
        /* NO CONFIGURATION implemented at the moment
        MenuItem root = tools.addItem("", FontAwesome.COG, null);
        root.addItem("Configure", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });
        root.addSeparator();
        root.addItem("Close", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Not implemented in this demo");
            }
        });
        */

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

        card.addComponents(toolbar, content);
        slot.addComponent(card);
        return slot;
    }
	
	private void toggleMaximized(final Component panel, final boolean maximized) {
        for (Iterator<Component> it = getRootVerticalLayout().iterator(); it.hasNext();) {
            it.next().setVisible(!maximized);
        }
        getDashboardPanelsCssLayout().setVisible(true);

        for (Iterator<Component> it = getDashboardPanelsCssLayout().iterator(); it.hasNext();) {
            Component c = it.next();
            c.setVisible(!maximized);
        }

        if (maximized) {
            panel.setVisible(true);
            panel.addStyleName("max");
        } else {
            panel.removeStyleName("max");
        }
    }
	
	@Getter
	protected class SlotComponent extends CssLayout {
		MenuBar tools = new MenuBar();
		
		public SlotComponent() {
			init();
		}
		
		private void init() {
			tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		}
	}
}
