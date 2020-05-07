package stackOverFlowExample;

import java.util.ArrayList;
import java.util.function.Function;

import javafx.beans.value.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;


public class TableViewComboBoxes extends Application {
	
    /* table stuff */
    private TableView<FieldMappingRow> tableView = new TableView<>(); //addTableView(); // 
    private ObservableList<FieldMappingRow> tableData = FXCollections.observableArrayList(new FieldMappingRow(false, "Source", "Target", "String", "Equals", "0", "", ""));
    
    /* global table column */
    private TableColumn<FieldMappingRow, StringProperty> colSource = new TableColumn<>("Source");
    
    /* global observable list choices, gets populated later */ 
	private ObservableList<String> choices = FXCollections.observableArrayList("");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		tableView.setItems(tableData);
		colSource.setCellValueFactory(new PropertyValueFactory<>("source"));
		
		/* hboxes for top and bottom of border pane */
		HBox top = new HBox();
		HBox bottom = new HBox();

		/* main stage */
        BorderPane root = new BorderPane();
        ComboBox<String> cmBoxWithEvent, cmBoxOnTop;
        cmBoxWithEvent= new ComboBox();
        cmBoxOnTop = new ComboBox();
        
        /* add options to event driving combo box */
        cmBoxWithEvent.getItems().add("");
        cmBoxWithEvent.getItems().add("select me");
 
        /* set editable to filterable combo box on the top */
        cmBoxOnTop.setEditable(true);
        
        /*labels */
        Label lblSelectTable, lblSelectPKey;  
        lblSelectTable = new Label("Event Combo Box: ");  
        lblSelectPKey = new Label("Regular Combo Box: ");  
        
        /* buttons */
        Button btnClickMe = new Button("Generate Observable List");
        Button btnNewRow = new Button("New Row");
        
        /* button events */
        /* new row */
		btnNewRow.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FieldMappingRow newRow = new FieldMappingRow(true, "blank", "blank", "String", "Equals", "0", "", "");
				// fieldMappingsTableView.getItems().add(newRow);
				tableData.add(newRow);

				tableView.refresh();
			}
		});
        
		/* click me - sets the field mapping choices into the table */
        btnClickMe.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent onChange) {
				
				tableData.clear();
				for (int i = 0; i < choices.size(); i++) {

					/* Then create new field mapping row */
					FieldMappingRow newRow = new FieldMappingRow(true,
							choices.get(i),
							"target", "String",
							"Equals", "0", "", "");
					
					/* add new data to table*/
					tableData.add(newRow);
				}
				
				tableView.setItems(tableData);
				
				tableView.refresh();
			}
        });
        
        
        
        cmBoxWithEvent.valueProperty().addListener(new ChangeListener<String>() { 
        	@Override
			public void changed(ObservableValue<? extends String> observable, String oldVal, String newVal) {
        		
        		/* Select the selected item */
				String selected = cmBoxWithEvent.getSelectionModel().getSelectedItem().toString();
        		if( selected.equals("select me")) {
        	        choices = FXCollections.observableArrayList("First choice", "Second choice", 
        	        		"Third choice",	"Fourth choice", "Fifth choice", 
        	        		"Sixth choice", "Seventh choice", "Eighth choice",
        	        		"Ninth choice", "Tenth choice", "Eleventh choice",
        	        		"Twelvth choice", "Thirteenth choice", "Fourteenth choice",
        	        		"Fifteenth choice", "Sixteenth choice", "Seventeenth choice",
        	        		"Eighteenth choice", "Nineteenth choice", "Twentieth choice");
        		}
        		
        		/* Filtered Lists wrapping the Observable List */
        		FilteredList<String> filteredItemsForTopComboBox = new FilteredList<String>(choices, p -> true);
        		//FilteredList<String> filteredItemsForTableComboBox = new FilteredList<String>(choices, p -> true);
        		
        		/* first set the combo box on top with all of it's options and make it filterable */
        		cmBoxOnTop.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
					
					/* display menu as user starts typing */
					//cmBoxSourceKeyField.show();
		            final TextField editor = cmBoxOnTop.getEditor();
		            final String selectedTopCmBox = (String) cmBoxOnTop.getSelectionModel().getSelectedItem();

		            // This needs run on the GUI thread to avoid the error described
		            // here: https://bugs.openjdk.java.net/browse/JDK-8081700.
		            Platform.runLater(() -> {
		                // If the no item in the list is selected or the selected item
		                // isn't equal to the current input, we refilter the list.
		                if (selectedTopCmBox == null || !selectedTopCmBox.equals(editor.getText())) {
		                	filteredItemsForTopComboBox.setPredicate(item -> {
		                        // We return true for any items that starts with the
		                        // same letters as the input. We use toUpperCase to
		                        // avoid case sensitivity.
		                        if (item.toUpperCase().startsWith(newValue.toUpperCase())) {
		                            return true;
		                        } else {
		                            return false;
		                        }
		                    });
		                }
		            });
		        });
				
				/* set attributes into target key field combo box */
        		cmBoxOnTop.setItems(filteredItemsForTopComboBox);
        		
        		colSource.setCellValueFactory(new Callback<CellDataFeatures<FieldMappingRow, StringProperty>, ObservableValue<StringProperty>>() {
        			@Override
        			public ObservableValue<StringProperty> call(final CellDataFeatures<FieldMappingRow, StringProperty> p) {
        				
            			final StringProperty val = p.getValue().sourceProperty();
            			
            			//binding to constant value
            			return Bindings.createObjectBinding(() -> val);
        				
        			}
        		});
        		
        		
        		colSource.setCellFactory(
        			new Callback< TableColumn< FieldMappingRow, StringProperty >, TableCell < FieldMappingRow, StringProperty > >() {
        				@Override
        				public TableCell< FieldMappingRow, StringProperty > call(final TableColumn < FieldMappingRow, StringProperty> tblColumn) {
        					
        					/* Table Cell */
        					TableCell<FieldMappingRow, StringProperty> cell = new TableCell<FieldMappingRow, StringProperty>();
		        			
        					/* Filtered list for every cell */
		        			FilteredList<String> filteredItemsForTableComboBox = new FilteredList<String>(choices, p -> true);
		        			final ComboBox<String> cmBox = new ComboBox<>(filteredItemsForTableComboBox);
		        			
		        			/* The important part of having editable combo boxes */
		        			cmBox.setEditable(true);
		        			
		        			/* when cell is null, put nothing there otherwise graphics property set to a combo box */
		        			cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(cmBox));
		        			
		        			/* set cells item property listener */
		        			cell.itemProperty().addListener(new ChangeListener<StringProperty>() {
		        				@Override
								public void changed(ObservableValue<? extends StringProperty> observable,
										StringProperty oldValue, StringProperty newValue) {	
		        						
	        						/* out with the old, in with the new */
		        					if(oldValue != null) {
		        						cmBox.valueProperty().unbindBidirectional(oldValue);
		        					}if(newValue != null) {
		        						cmBox.valueProperty().bindBidirectional(newValue);
		        					}				 
		        				}
							});
		        			
		        			/* on click of combo box select text and drops down */
		        			cmBox.getEditor().focusedProperty().addListener(
		        				(obser, oldVal, newVal) -> {
		        					if(newVal) {
		        						Platform.runLater(() -> {
		        							cmBox.getEditor().selectAll();
		        							cmBox.show();
		        						});
		        					}
		        				}
		        			);
		        			
		        			/* this is the filtering sauce */
		        			cmBox.getEditor().textProperty().addListener(new ChangeListener<String>(){
		        				@Override
		        				public void changed(ObservableValue<? extends String> obsV, String oldV, String newV) {
			        				/* newV is what is getting typed in, item is what was there */

			        	            final TextField editor = cmBox.getEditor();
			        	            final String tblBoxSelected = (String) cmBox.getSelectionModel().getSelectedItem();
			        	            	
		        	                /* if the no item in the list is selected or the selected item
		        	                 isn't equal to the current input, we refilter the list. */
		        	            	if (tblBoxSelected == null || !tblBoxSelected.equals(editor.getText())) {
		        	                	filteredItemsForTableComboBox.setPredicate(item -> {
		        	                        /* return true for any items that starts with the
		        	                        same letters as the input. We use toUpperCase to
		        	                        avoid case sensitivity. */
		        	                		if(!newV.equals("") && newV!=null) {
			        	                        if (item.toUpperCase().startsWith(newV.toUpperCase())) {
			        	                            return true;
			        	                        } else{
			        	                            return false;
			        	                        }
		        	                		}else {
		        	                			return true;
		        	                		}
		        	                    });
		        	                }
		        				}
		        	        });
		        			cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		
		        			return cell;
        			}
        		});
        	}
        });
        
        
        
        colSource.setPrefWidth(150);
        tableView.getColumns().add(colSource);
        
        top.getChildren().addAll(lblSelectTable, cmBoxWithEvent, lblSelectPKey, cmBoxOnTop);
        bottom.getChildren().addAll(btnClickMe, btnNewRow);

        root.setTop(top);
        root.setCenter(tableView);
        root.setBottom(bottom);
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
	}
	
	public static void main(String[] args) {
        launch(args);
    }

}



