/** Quien puede votar en una reunión para expulsar a un sospechoso. */
public interface Votable {
    void votar(Tripulante sospechoso);
}
