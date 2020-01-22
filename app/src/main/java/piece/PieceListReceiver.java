package piece;

import java.util.List;

public interface PieceListReceiver {
    /**
     * this method receives a list of pieces
     * @param pieces
     */
    void receivePieces(List<Piece> pieces);
}
