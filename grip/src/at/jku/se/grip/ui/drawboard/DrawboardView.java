package at.jku.se.grip.ui.drawboard;

import org.vaadin.hezamu.canvas.Canvas;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.enums.FloorType;

@SuppressWarnings("serial")
public class DrawboardView extends CustomComponent {

	private HorizontalLayout mainLayout = null;
	private VerticalLayout drawLayout = null;
	private HorizontalLayout canvasLayout = null;
	private Label headerLabel = null;
	private FormLayout selectMenuFormLayout = null;
	private ComboBox selectPathComboBox = null;
	private ComboBox selectRobotComboBox = null;
	private ComboBox floorTypeComboBox = null;
	private HorizontalLayout selectDeleteButtons = null;
	private Button selectButton = null;
	private Button deleteButton = null;
	private TextField pathName = null;
	private HorizontalLayout saveClearButtons = null;
	private Button saveButton = null;
	private Button clearButton = null;
	private Button executeButton = null;
	private Canvas canvas = null;
	private VerticalLayout rootVerticalLayout = null;
	private HorizontalLayout headerHorizontalLayout = null;

	public DrawboardView() {
		super();
		init();
	}

	private void init() {
		this.setCompositionRoot(getRootVerticalLayout());
		this.setSizeFull();
	}

	public VerticalLayout getRootVerticalLayout() {
		if(rootVerticalLayout == null) {
			rootVerticalLayout = new VerticalLayout();
			rootVerticalLayout.setSizeFull();
			rootVerticalLayout.setMargin(true);
			rootVerticalLayout.addStyleName("dashboard-view");
			rootVerticalLayout.addComponent(getHeaderHorizontalLayout());
			rootVerticalLayout.addComponent(getMainLayout());
			rootVerticalLayout.setExpandRatio(getMainLayout(), 1f);
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
			headerLabel = new Label("Drawboard");
			headerLabel.setSizeUndefined();
			headerLabel.addStyleName(ValoTheme.LABEL_H1);
			headerLabel.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		}
		return headerLabel;
	}
	
	public HorizontalLayout getMainLayout() {
		if (mainLayout == null) {
			mainLayout = new HorizontalLayout();
			mainLayout.setSizeFull();

			mainLayout.addComponent(getSelectMenu());
			mainLayout.setComponentAlignment(getSelectMenu(), Alignment.TOP_LEFT);
			mainLayout.addComponent(getDrawLayout());

			mainLayout.setExpandRatio(selectMenuFormLayout, 1);
			mainLayout.setExpandRatio(drawLayout, 3);
		}
		return mainLayout;
	}

	public VerticalLayout getDrawLayout() {
		if (drawLayout == null) {
			drawLayout = new VerticalLayout();
			drawLayout.setSizeFull();
			drawLayout.addComponent(getCanvasLayout());
			drawLayout.setExpandRatio(getCanvasLayout(), 1);
		}
		return drawLayout;
	}

	public HorizontalLayout getCanvasLayout() {
		if (canvasLayout == null) {
			canvasLayout = new HorizontalLayout();
			canvasLayout.setWidth("880px");
			canvasLayout.setHeight("620px");
			canvasLayout.addComponent(getCanvas());
			canvasLayout.addStyleName("card");
		}
		return canvasLayout;
	}

	public FormLayout getSelectMenu() {
		if (selectMenuFormLayout == null) {
			selectMenuFormLayout = new FormLayout();
			selectMenuFormLayout.setMargin(true);

			selectMenuFormLayout.addComponent(getSelectPathComboBox());
			selectMenuFormLayout.addComponent(getSelectDeleteButtons());
			selectMenuFormLayout.addComponent(getPathName());
			selectMenuFormLayout.addComponent(getSaveCancelButtons());
			selectMenuFormLayout.addComponent(getFloorTypeComboBox());
			selectMenuFormLayout.addComponent(getSelectRobotComboBox());
			selectMenuFormLayout.addComponent(getExecuteButton());
		}
		return selectMenuFormLayout;
	}

	public ComboBox getSelectPathComboBox() {
		if (selectPathComboBox == null) {
			selectPathComboBox = new ComboBox();
			selectPathComboBox.addStyleName("vertical");
			selectPathComboBox.setItemCaptionPropertyId("name");
			selectPathComboBox.setInputPrompt("Choose path");
		}
		return selectPathComboBox;
	}
	
	public ComboBox getSelectRobotComboBox() {
		if (selectRobotComboBox == null) {
			selectRobotComboBox = new ComboBox();
			selectRobotComboBox.addStyleName("vertical");
			selectRobotComboBox.setItemCaptionPropertyId("name");
			selectRobotComboBox.setInputPrompt("Execute on");
		}
		return selectRobotComboBox;
	}
	
	public ComboBox getFloorTypeComboBox() {
		if(floorTypeComboBox == null) {
			floorTypeComboBox = new ComboBox();
			floorTypeComboBox.addStyleName("vertical");
			floorTypeComboBox.setInputPrompt("Floor type");
			floorTypeComboBox.setContainerDataSource(new BeanItemContainer<>(
                FloorType.class, FloorType.valuesAsList()));
		}
		return floorTypeComboBox;
	}
	
	private HorizontalLayout getSelectDeleteButtons() {
		if (selectDeleteButtons == null) {
			selectDeleteButtons = new HorizontalLayout();
			selectDeleteButtons.setSpacing(true);
			selectDeleteButtons.addComponent(getSelectButton());
			selectDeleteButtons.addComponent(getDeleteButton());
		}
		return selectDeleteButtons;
	}

	public TextField getPathName() {
		if (pathName == null) {
			pathName = new TextField();
			pathName.setInputPrompt("Pathname");
		}
		return pathName;
	}

	private HorizontalLayout getSaveCancelButtons() {
		if (saveClearButtons == null) {
			saveClearButtons = new HorizontalLayout();
			saveClearButtons.setSpacing(true);
			saveClearButtons.addComponent(getSaveButton());
			saveClearButtons.addComponent(getClearButton());
		}
		return saveClearButtons;
	}

	public Button getSaveButton() {
		if (saveButton == null) {
			saveButton = new Button("Save");
			saveButton.setIcon(FontAwesome.FLOPPY_O);
		}
		return saveButton;
	}

	public Button getSelectButton() {
		if (selectButton == null) {
			selectButton = new Button("Select");
			selectButton.setIcon(FontAwesome.CHECK);
		}
		return selectButton;
	}

	public Button getDeleteButton() {
		if (deleteButton == null) {
			deleteButton = new Button("Delete");
			deleteButton.setIcon(FontAwesome.TRASH);
		}
		return deleteButton;
	}

	public Button getClearButton() {
		if (clearButton == null) {
			clearButton = new Button("Clear");
			clearButton.setIcon(FontAwesome.BAN);
		}
		return clearButton;
	}
	
	public Button getExecuteButton() {
		if(executeButton == null) {
			executeButton = new Button("Execute");
			executeButton.setIcon(FontAwesome.PLAY);
		}
		return executeButton;
	}

	public Canvas getCanvas() {
		if (canvas == null) {
			canvas = new Canvas();
			canvas.setSizeFull();
		}
		return canvas;
	}

}
