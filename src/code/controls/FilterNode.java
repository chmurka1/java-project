package code.controls;

import code.filters.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/***
 * Standard node for applying a simple filter on an image
 */
public class FilterNode extends AbstractNode {

    Filter filter;

    @FXML
    Pane topPane;

    boolean isExtended;
    TextField textField;
    Button setButton;
    GridPane gridPane;

    public FilterNode(Canvas canvas) {
        super(canvas);
        input1 = new TargetSocket(this);
        input1.setName("Input");
        addInputSocket(input1);
        output1 = new SourceSocket(this);
        output1.setName("Output");
        addOutputSocket(output1);

        isExtended=false;
        textField=new TextField();
        textField.setMaxWidth(100);
        setButton=new Button("Set");
        gridPane=new GridPane();
        gridPane.add(textField,0,0);
        gridPane.add(setButton,1,0);

        String[] listOfFilters ={"empty filter","brighten image","darken image", "black and white",
                "sharpen","contrast","saturate","horizontal blur","vertical blur","trim top"};

        ComboBox<String> comboBox= new ComboBox<>(FXCollections.observableArrayList(listOfFilters));
        comboBox.setOnAction(e -> {
            if(comboBox.getValue().equals("empty filter")){
                shrink();
                filter=Filters.empty;
            }
            if(comboBox.getValue().equals("brighten image")){
                shrink();
                filter=Filters.brightenImage;
            }
            if(comboBox.getValue().equals("darken image")){
                shrink();
                filter= Filters.darkenImage;
            }
            if(comboBox.getValue().equals("black and white")){
                shrink();
                filter = Filters.saturate(-100);
            }
            // advised range from -100 to 100
            if(comboBox.getValue().equals("sharpen")){
                extend();
                setButton.setOnAction(ee -> filter = Filters.sharpen(getCoefficient()) );
            }
            // advised range from -100 to +100
            if(comboBox.getValue().equals("contrast")){
                extend();
                setButton.setOnAction(ee -> filter = Filters.contrast(getCoefficient()) );
            }
            // advised range from -100 to +100
            if(comboBox.getValue().equals("saturate")){
                extend();
                setButton.setOnAction(ee -> filter = Filters.saturate(getCoefficient()) );
            }
            //coefficient in per mille
            // advised range from -100 to +100. If negative, takes abs value
            if(comboBox.getValue().equals("horizontal blur")){
                extend();
                setButton.setOnAction(ee -> filter = new HorizontalBlur(getCoefficient()) );
            }
            if(comboBox.getValue().equals("vertical blur")){
                extend();
                setButton.setOnAction(ee -> filter = new VertBlur(getCoefficient()) );
            }
            //coefficient in %
            if(comboBox.getValue().equals("trim top")){
                extend();
                setButton.setOnAction(ee -> filter = new TrimTop(getCoefficient()) );
            }
        });
        topPane.getChildren().add(comboBox);

        title.setText("Filter");
    }

    @Override
    public void compute() {
        filter.apply(this);
        ready = true;
    }

    @Override
    public boolean checkInput(){
        return input1.getContent()!=null;
    }

    private void extend(){
        textField.clear();
        if(!isExtended) {
            topPane.getChildren().add(gridPane);
            isExtended = true;
        }
    }

    private void shrink(){
        if(isExtended){
            topPane.getChildren().remove(gridPane);
            isExtended=false;
        }
    }

    private int getCoefficient(){
        CharSequence seq=textField.getCharacters();
        int coefficient=0;
        try{
            coefficient=Integer.parseInt(seq.toString());
        }catch (NumberFormatException exc){
            System.out.println("not a number");
        }
        return coefficient;
    }

    /*public interface Filter {
        default BufferedImage filter( BufferedImage in ) {
            BufferedImage res = new BufferedImage(in.getWidth(),in.getHeight(),in.getType());
            for (int y = 0; y < in.getHeight(); y++) {
                for (int x = 0; x < in.getWidth(); x++) {
                    res.setRGB(x,y,get(in.getRGB(x,y)));
                }
            }
            return res;
        }
        int get(int p);
    }

    public static Filter blackAndWhite = p -> {
        int res = (((p & 0x00ff0000) >> 16) + ((p & 0x0000ff00) >> 8) + (p & 0x000000ff)) / 3;
        return res << 16 | res << 8 | res;
    };

    public static Filter saturate = p -> {
        int i = (((p & 0x00ff0000) >> 16) + ((p & 0x0000ff00) >> 8) + (p & 0x000000ff)) / 3;
        int r = Math.max(Math.min(2 * ((p & 0x00ff0000) >> 16) - i, 255), 0);
        int g = Math.max(Math.min(2 * ((p & 0x0000ff00) >> 8) - i, 255), 0);
        int b = Math.max(Math.min(2 * (p & 0x000000ff) - i, 255), 0);
        return r << 16 | g << 8 | b;
    };*/
}
