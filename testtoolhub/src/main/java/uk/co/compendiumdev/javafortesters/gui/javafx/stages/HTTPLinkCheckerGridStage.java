package uk.co.compendiumdev.javafortesters.gui.javafx.stages;

import javafx.beans.binding.When;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkChecker;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkQueue;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkQueueFileReader;
import uk.co.compendiumdev.javafortesters.domain.http.linkchecker.LinkToCheck;
import uk.co.compendiumdev.javafortesters.domain.launcher.LauncherUrlSet;
import uk.co.compendiumdev.javafortesters.gui.javafx.Config;
import uk.co.compendiumdev.javafortesters.gui.javafx.services.LinkCheckerNewTask;
import uk.co.compendiumdev.javafortesters.gui.javafx.utils.JavaFX;
import uk.co.compendiumdev.javafortesters.gui.urllauncher.PhysicalUrlLauncher;

import java.io.File;
import java.io.InputStream;
import java.util.Optional;


public class HTTPLinkCheckerGridStage extends Stage {

    private static HTTPLinkCheckerGridStage urlLauncherGridSingletonStage=null;
    private static Service<ObservableList<String>> service;
    LinkQueue defaultLinks;
    LinkChecker linksToCheck;

    public static void singletonActivate() {

        if(urlLauncherGridSingletonStage==null)
            urlLauncherGridSingletonStage = new HTTPLinkCheckerGridStage(false);

        urlLauncherGridSingletonStage.show();
        urlLauncherGridSingletonStage.requestFocus();
    }

    public static EventHandler<ActionEvent> getActivationEvent() {
        return
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        HTTPLinkCheckerGridStage.singletonActivate();
                    }
                };
    }



    public HTTPLinkCheckerGridStage(boolean hidden){

        // filename of file loaded | load |
        // check urls | save report

        HBox fileLoadControl = new HBox();
        TextField filename = JavaFX.textField("Default Resource File", "The file of URLs loaded");
        filename.setPrefWidth(400);
        Button loadUrlButton = JavaFX.button("Load", "Load a test file of URLs");
        fileLoadControl.getChildren().addAll(filename, loadUrlButton);
        fileLoadControl.setSpacing(10);

        VBox urlChecking = new VBox();
        HBox actionButtonsControl = new HBox();
            Button checkUrlsButton = JavaFX.button("Check URLs", "Check all the URLs");
            Button cancelCheckingUrlsButton = JavaFX.button("Cancel", "Cancel Checking URLs");
            //Button saveUrlReportButton = JavaFX.button("Save URL Report", "Save the URL report to file");
            Label progressPercentage = new Label("");
            progressPercentage.setVisible(false);
            ProgressBar progressBar = new ProgressBar();
            progressBar.setVisible(false);
            Label progressMessage = new Label("");
            progressMessage.setVisible(false);
            
        actionButtonsControl.getChildren().addAll(checkUrlsButton, cancelCheckingUrlsButton, progressPercentage, progressBar); //, saveUrlReportButton);
        actionButtonsControl.setSpacing(10);
        urlChecking.getChildren().addAll(actionButtonsControl, progressMessage);


        VBox buttonsControl = new VBox();
        buttonsControl.getChildren().addAll(fileLoadControl, urlChecking);

        // get the default data
        defaultLinks = loadDefaultLinks();

        //LauncherUrlLoader loader = new LauncherUrlLoader();
        //UrlLauncher urls = loader.load();

        final ObservableList<LinkToCheck> urlsToCheck = FXCollections.observableArrayList();
        urlsToCheck.addAll(defaultLinks.getLinks());

        //final ObservableList<LauncherUrl> urlsList = FXCollections.observableArrayList();
        //urlsList.addAll(profiles.get(0).getUrl());

        BorderPane root = new BorderPane();

        // left grid
        TableView leftside = new TableView();
        leftside.setEditable(false);
        leftside.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn urlColumn = new TableColumn("URLs");
        leftside.getColumns().add(urlColumn);

        urlColumn.setCellFactory(new Callback<TableColumn<LauncherUrlSet, String>, TableCell<LauncherUrlSet, String>>() {
            @Override
            public TableCell<LauncherUrlSet, String> call(TableColumn<LauncherUrlSet, String> col) {
                final TableCell<LauncherUrlSet, String> cell = new TableCell<LauncherUrlSet, String>() {
                    @Override
                    public void updateItem(String firstName, boolean empty) {
                        super.updateItem(firstName, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(firstName);
                        }
                    }
                };
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() > 1) {
                            // double click open URL - TODO investigation compilation problem with getITem()
                            LinkToCheck rowData = (LinkToCheck) cell.getTableRow().getItem().getUrls();
                            PhysicalUrlLauncher.launch(rowData.getUrl());
                        }else {
                            // single click ignore
                            // if click count = 1 then set the data on the right hand side of the grid
                            //urlsToCheck.clear();
                            //LauncherUrlSet rowData = (LauncherUrlSet)cell.getTableRow().getItem();
                            //urlsToCheck.addAll(rowData.getUrls().values());
                        }
                    }
                });
                return cell ;
            }
        });


        urlColumn.setCellValueFactory(new PropertyValueFactory<LauncherUrlSet,String>("url"));
        leftside.setItems(urlsToCheck);


        final TextArea textArea = new TextArea("");
        textArea.setWrapText(true);



        linksToCheck = new LinkChecker(defaultLinks);
        // Create the service
        service = new Service<ObservableList<String>>() {
            @Override
            protected Task<ObservableList<String>> createTask() {
                return new LinkCheckerNewTask(linksToCheck);
            }
        };

        textArea.textProperty().bind(service.valueProperty().asString());
        progressMessage.textProperty().bind(service.messageProperty());
        progressPercentage.textProperty().bind(new When(service.progressProperty().isEqualTo(-1))
                .then("Unknown")
                .otherwise(service.progressProperty().multiply(100.0)
                        .asString("%.2f%%")));
        progressBar.progressProperty().bind(service.progressProperty());
        
        // when close stage, stop the link checking
        this.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<javafx.event.Event>() {
            @Override
            public void handle(Event event) {
                stopServices();
            }
        });


        checkUrlsButton.disableProperty().bind(service.stateProperty().isEqualTo(Worker.State.RUNNING));
        cancelCheckingUrlsButton.disableProperty().bind(service.stateProperty().isNotEqualTo(Worker.State.RUNNING));


        cancelCheckingUrlsButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        service.cancel();
                    }
                });

        checkUrlsButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        try {
                            service.reset();

                            if(linksToCheck.hasLinksToCheck() && linksToCheck.getCurrentLinkNumber()!=0){
                                if(!doYouWantToContinue()) {
                                    linksToCheck.reset();
                                }
                            }else{
                                linksToCheck.reset();
                            }

                            progressMessage.setVisible(true);
                            progressPercentage.setVisible(true);
                            progressBar.setVisible(true);
                            service.start();
                        } catch (Exception ex) {
                            JavaFX.alertErrorDialogWithException(ex);
                        }

                    }
                });


        Stage currentStage = this;
        loadUrlButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        FileChooser fileChooser = new FileChooser();
                        File file = fileChooser.showOpenDialog(currentStage);
                        if (file != null) {
                            if(file.exists()) {
                                filename.setText(file.getName());
                                filename.setTooltip(new Tooltip(file.getAbsolutePath()));
                                LinkQueue links = loadLinks(file);
                                if(links.numberInQueue()>0){
                                    defaultLinks.getLinks().clear();
                                    defaultLinks.getLinks().addAll(links.getLinks());
                                    urlsToCheck.clear();
                                    urlsToCheck.addAll(defaultLinks.getLinks());
                                    linksToCheck.replaceLinks(defaultLinks);
                                }
                            }
                        }
                    }
                });

            HBox form = new HBox();
            form.getChildren().addAll(leftside, textArea);
            form.setHgrow(textArea, Priority.ALWAYS);


            root.setTop(buttonsControl);
            root.setCenter(form);

            Scene scene = new Scene(root, Config.getDefaultWindowWidth(), Config.getDefaultWindowHeight());
            this.

            setTitle("Simple Link Checker");

            this.setScene(scene);

            if(!hidden)
                this.show();


        }

    private boolean doYouWantToContinue() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Continue Checking?");
        alert.setHeaderText("You have checked " + linksToCheck.getCurrentLinkNumber() + " links " +
                            "out of " + linksToCheck.getNumberInQueue() + ". \n" +
                            "Do you want to continue from where you left off, \n"+
                            "or do you want to start again?");
        alert.setContentText("Continue? Or Restart to start again.");

        ButtonType bContinue = new ButtonType("Continue");
        ButtonType bRestart  = new ButtonType("Restart");

        alert.getButtonTypes().setAll(bContinue, bRestart);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == bContinue;
    }

    private LinkQueue loadDefaultLinks() {
        LinkQueue links = new LinkQueue();

        String filenameForResource = "defaultLinksToCheck.txt";

        try {

            InputStream fileToRead = HTTPLinkCheckerGridStage.class.getResourceAsStream(filenameForResource);

            if(fileToRead!=null) {
                LinkQueueFileReader fileReader = new LinkQueueFileReader(fileToRead, "Resource: " + filenameForResource);
                links = fileReader.getQueue();
            }

        }catch(Exception e){
            JavaFX.alertErrorDialogWithException(e, "Error Loading Default Resource File " + filenameForResource);
        }

        return links;
    }

    private LinkQueue loadLinks(File linkQueueFile) {
        LinkQueue links = new LinkQueue();

        try {

            if(linkQueueFile!=null) {
                LinkQueueFileReader fileReader = new LinkQueueFileReader(linkQueueFile);
                links = fileReader.getQueue();
            }

        }catch(Exception e){
            JavaFX.alertErrorDialogWithException(e, "Error Loading File " + linkQueueFile.getAbsolutePath());
        }

        return links;
    }

    public static void stopServices() {
        if(service!=null){
            if(service.getState()== Worker.State.RUNNING)
                service.cancel();
        }
    }
}
