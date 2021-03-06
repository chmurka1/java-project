package code.graph;

import code.controls.*;
import code.controls.InputNode;

public class Compute {
    public static void compute(Canvas canvas){
        innerClear(canvas);

        for(InputNode inputNode : canvas.listOfInputNodes)
            if( !inputNode.ready ) System.out.println("loading images from disk");


        boolean flag=true;
        while(flag){
            flag=false;

            for(AbstractNode node : canvas.listOfNodes)   {
                if( node.ready )    continue;
                if(node.checkInput()){
                    node.compute();
                    flag=true;
                }
            }
        }

        for(OutputNode outputNode: canvas.listOfOutputNodes){
            if(outputNode.input.getContent() != null) {
                System.out.println("output ready");
                outputNode.colorTitlePane();
            }
        }
    }

    private static void innerClear(Canvas canvas){
        canvas.clickedSocket=null;
        for(AbstractNode node : canvas.listOfNodes){
            node.clear();
        }
        for(OutputNode outputNode:canvas.listOfOutputNodes){
            outputNode.clear();
        }
    }

    public static void fullClear(Canvas canvas){
        canvas.clear();
    }
}
