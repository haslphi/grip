package at.jku.se.grip.ui.drawboard;

import static at.jku.se.grip.common.Constants.CANVAS_HEIGHT;
import static at.jku.se.grip.common.Constants.CANVAS_WIDTH;

import org.vaadin.hezamu.canvas.Canvas;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import at.jku.se.grip.enums.FloorType;

@SuppressWarnings("serial")
public class DrawboardView extends VerticalLayout {

	private HorizontalLayout mainLayout = null;
	private VerticalLayout drawLayout = null;
	private VerticalLayout canvasLayout = null;
	private Label headerLabel = null;
	private VerticalLayout selectMenuFormLayout = null;
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
	private HorizontalLayout headerHorizontalLayout = null;

	public DrawboardView() {
		super();
		init();
	}

	private void init() {
		this.setSizeFull();
		this.setMargin(true);
		this.addStyleName("drawboard");
		this.addComponent(getHeaderHorizontalLayout());
		this.addComponent(getMainLayout());
		this.setExpandRatio(getMainLayout(), 1f);
		this.setSizeFull();
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
			Label size = new Label("200cm x 150cm");
			size.setWidth("100%");
			size.addStyleName(ValoTheme.LABEL_BOLD);
			drawLayout.addComponent(size);
			drawLayout.addComponent(getCanvasLayout());
			drawLayout.setComponentAlignment(size, Alignment.TOP_CENTER);
			drawLayout.setExpandRatio(getCanvasLayout(), 1);
		}
		return drawLayout;
	}

	public VerticalLayout getCanvasLayout() {
		if (canvasLayout == null) {
			canvasLayout = new VerticalLayout();
			canvasLayout.setWidth(CANVAS_WIDTH + "px");
			canvasLayout.setHeight(CANVAS_HEIGHT + "px");
			canvasLayout.addComponent(getCanvas());
			canvasLayout.addStyleName("card");
		}
		return canvasLayout;
	}

	public VerticalLayout getSelectMenu() {
		if (selectMenuFormLayout == null) {
			selectMenuFormLayout = new VerticalLayout();
			selectMenuFormLayout.setMargin(false);
			selectMenuFormLayout.setSpacing(true);

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
