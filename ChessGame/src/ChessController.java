public class ChessController {
    private ChessModel model;
    private ChessView view;

    public ChessController(ChessModel model, ChessView view) {
        this.model = model;
        this.view = view;
    }

    public void updateView(){
        view.printBoard(model.getBoardFields());
    }

}
