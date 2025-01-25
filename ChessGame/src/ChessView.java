import java.util.HashMap;

public class ChessView {
    public void printBoard(HashMap<String, String> boardFields) {
        String[] horizontalCoordinates = {"a","b","c","d","e","f","g","h"};
        for(int i=8; i>0; i--){
            for(int j=0; j<8; j++){
               String figure = boardFields.get(horizontalCoordinates[j]+i);
               if(figure == null){
                   System.out.print(".");
                   continue;
               }
               System.out.print(figure);
            }
            System.out.print("\n");
        }
    }
}
