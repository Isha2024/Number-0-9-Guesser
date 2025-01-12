import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class InteractiveDrawing {
   
    static ArrayList<ArrayList<ArrayList<Double[]>>> listOfLists = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num0 = new ArrayList<>();
  
    static ArrayList<ArrayList<Double[]>> num1 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num2= new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num3 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num4 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num5 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num6 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num7 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num8 = new ArrayList<>();
    static ArrayList<ArrayList<Double[]>> num9 = new ArrayList<>();
  
    public static void addimagepointstolist(int i,ArrayList<Double[]> pointss){
        listOfLists.get(i).add(pointss);  
    }
   
    
    public static void setarrays(){
       
        listOfLists.add(num0);
        listOfLists.add(num1);
        listOfLists.add(num2);
        listOfLists.add(num3);
        listOfLists.add(num4);
        listOfLists.add(num5);
        listOfLists.add(num6);
        listOfLists.add(num7);
        listOfLists.add(num8);
        listOfLists.add(num9);
        
        
    }
  
    
        public static Double[] findBoundingBox(ArrayList<Double[]> points, int i) {
            if (points.isEmpty()) {
                System.out.println("No points found.");
                return new Double[0];  // Return an empty array if there are no points
            }
    
            // Initialize variables to extreme values
            Double minX = Double.MAX_VALUE;
            Double minY = Double.MAX_VALUE;
            Double maxX = Double.MIN_VALUE;
            Double maxY = Double.MIN_VALUE;
    
            // Iterate through points to find min and max coordinates
            for (Double[] point : points) {
                Double x = point[0];
                Double y = point[1];
                if (x < minX) minX = x;
                if (y < minY) minY = y;
                if (x > maxX) maxX = x;
                if (y > maxY) maxY = y;
                double width=maxX-minX;
                double height=maxY-minY;
                
               if(i==1) {if (minX-width/2>=0){
                    minX=minX-width/2;
                }
                if (minY-height/2>=0){
                    minX=minX-height/2;
                }
                if (maxX+width/2<=50){
                    maxX=maxX+width/2;
                }
                if (maxY+height/2<=50){
                    maxY=maxY+height/2;
                }
            }

            }
    
            // Return the bounding box as an array: [minX, minY, maxX, maxY]
            return new Double[] { minX, minY, maxX, maxY };
        }

        public static boolean areBoxesEqual(Double[] box1, Double[] box2) {
            // Check if both boxes are valid (have exactly 4 elements)
            if (box1.length != 4 || box2.length != 4) {
                System.out.println("Invalid bounding box dimensions.");
                return false;
            }
        
            // Compare each corresponding element in the bounding boxes
            return box2[0]>=(box1[0]) &&  // minX
                   box2[1]>=(box1[1]) &&  // minY
                   box2[2]<=(box1[2]) &&  // maxX
                   box2[3]<=(box1[3]);    // maxY
        }




    public static double checker(int p,ArrayList<Double[]> points ){
        double total=0;
        Map<List<Double>, Integer> xymap = new HashMap<>();
        if (listOfLists.get(p).isEmpty()) {
            System.out.println("List is empty, skipping checker for this index");
            return 0.0; // Or handle this case accordingly
        }
        
          double percent=0;
         
        for (int i = 0; i < listOfLists.get(p).size(); i++) {
            System.out.println("checking: " + p);
              if(areBoxesEqual(findBoundingBox(listOfLists.get(p).get(i),1),findBoundingBox(points,0)) && !(listOfLists.get(p).get(i).isEmpty())){
            for (int j = 0; j < listOfLists.get(p).get(i).size(); j++) {
                  if(!(listOfLists.get(p).get(i).get(j).length==0)){
                    Double[] point = listOfLists.get(p).get(i).get(j);
                  
                    Double x = point[0];  // x-coordinate
                    Double y = point[1];  // y-coordinate
                  
                    List<Double> xyPoint = Arrays.asList(x, y);
                    if (!xymap.containsKey(xyPoint)) {
                        xymap.put(xyPoint, 1);  // Initialize count as 1
                        total++;
                    } else {
                        xymap.put(xyPoint, xymap.get(xyPoint) + 1);  // Increment count
                        total++;
                    }
                    
                }
                  }
                
                
           }
        }
        
        
       
        double frequency=0;
        
        if(xymap.isEmpty()){
            System.out.println("Not enough data to make a guess for number: " + p);
           }

        if(!(xymap.isEmpty())){
       frequency=total/xymap.size();
        }
        for (Map.Entry<List<Double>, Integer> entry : xymap.entrySet()) {
            
           System.out.println(entry.getKey() + " " + entry.getValue());
          
        }
         // Using an iterator
        for (Map.Entry<List<Double>, Integer> entry : xymap.entrySet()) {
            
            if(entry.getValue()<frequency){
            xymap.remove(entry.getValue(), entry.getKey());
            }
        }
       
        List<List<Double>> keys = new ArrayList<>(xymap.keySet());
       

        double k=0;
        for(int w=0; w<keys.size();w++){
            if(isPointDrawn(keys.get(w).get(0),keys.get(w).get(1),points)){
             k++;
             System.out.println("point match");
            }
        }
       if(keys.size()!=0) {percent=k/keys.size();}
        if(percent>.9){
            System.out.println(percent);
            return percent;
        }
        else 
        {
            
            return -1;
        }
    

    }
     

    public static boolean isPointDrawn(double x, double y, ArrayList<Double[]> points) {
        for (Double[] point : points) {
            if (Math.abs(point[0] - x) < 1.0 && Math.abs(point[1] - y) < 1.0) {
                
                return true;
            }
        }
       
        return false;
    }

    
        public static void main(String[] args) {
            setarrays();
           
            Scanner scanner2 = new Scanner(System.in);
            while(!(scanner2.nextLine().equals("leave"))){
            
            int comguess=0;
            ArrayList<Double[]> points = new ArrayList<>();
            StdDraw.setCanvasSize(400, 400);  // Set up canvas
            StdDraw.setXscale(0, 50);  // Coordinate system
            StdDraw.setYscale(0, 50);
            
            System.out.println("Click and drag to draw. Press 'q' to quit.");
            
            boolean drawing = true;
            double prevX = 0, prevY = 0;
            
            // Drawing loop
            while (drawing) {
                if (StdDraw.isMousePressed()) {
                    double x = StdDraw.mouseX();
                    double y = StdDraw.mouseY();

                    if (prevX == -1 && prevY == -1) {
                        prevX = x;
                        prevY = y;
                    } else if (Math.abs(prevX - x) > 0.1 || Math.abs(prevY - y) > 0.1) {
                        points.add(new Double[]{x, y, 0.0 , 0.0 , 0.0 , 0.0, 0.0 , 0.0, 0.0 , 0.0});
                       
                        StdDraw.line(prevX, prevY, x, y);  // Draw the line between points
                        prevX = x;
                        prevY = y;
                    }
                } else {
                    // Reset when the mouse is released
                    prevX = -1;
                    prevY = -1;
                }
    
    
                if (StdDraw.hasNextKeyTyped()) {
                    char key = StdDraw.nextKeyTyped();
                    if (key == 'q') {
                        drawing = false;
                        Double[] boundingBox = findBoundingBox(points,0);
                        for(int j=0; j<points.size();j++){
                           points.get(j)[2]=boundingBox[2];
                           points.get(j)[3]=boundingBox[1];
                           points.get(j)[4]=boundingBox[0];
                           points.get(j)[5]=boundingBox[1];
                           points.get(j)[6]=boundingBox[0];
                           points.get(j)[7]=boundingBox[3];
                           points.get(j)[8]=boundingBox[2];
                           points.get(j)[9]=boundingBox[3];

                        }
                      
                        System.out.println("Quitting and saving drawing...");
                      //  StdDraw.save("drawing" + i + ".png");  // Save the drawing as a PNG
                        
                        
                       
                    }
                }
            }
          
           System.out.print("guessing the number..");
         double min=0;
        
          for(int g=0; g<10;g++){
            double result = checker(g, points);
           if (result > min) {
             min = result;
             comguess = g;
                 }

          }
           System.out.println("Is this the number? : " + comguess);
           String answer = scanner2.nextLine();
           

           if(answer.equals("y")){
            System.out.println("Great! Click any key to play again! ");
            addimagepointstolist(comguess,points);

           }
           if(answer.equals("n")){
            System.out.println("Which number is it?");
            String answer2 = scanner2.nextLine();
            int intans2=Integer.parseInt(answer2);
            addimagepointstolist(intans2,points);
            System.out.print("Click any key so the guesser can try again!");
           }

           //ask it to select

            }

        }
    }

    


