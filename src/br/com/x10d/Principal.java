package br.com.x10d;

import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Principal extends Application {

	private static final int LARGURA_ALTURA_TELA = 500;
	private static final int TAMANHO_QUADRADO = LARGURA_ALTURA_TELA / 5;
	private static final int FUNDO = TAMANHO_QUADRADO - 3;
	private int quantidadeJogadas = 1;
	private Text text;
	private Group root;
	private static final String GANHOU_X = "XXX";
	private static final String GANHOU_O = "OOO";
	private HashMap<String, String> mapa = new HashMap<>();
	private Button buttonNovoJogo;
	private static final String NOVO_JOGO = "Novo Jogo";
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		root = new Group();

		Scene scene = new Scene(root, LARGURA_ALTURA_TELA, LARGURA_ALTURA_TELA);
		scene.setFill(new LinearGradient(0, 0, 1, 1, true, // sizing
				CycleMethod.NO_CYCLE, // cycling
				new Stop(0, Color.web("#81c483")), // colors
				new Stop(1, Color.web("#fcc200"))));

		criaBoard();

		text = new Text();
		text.setLayoutX(LARGURA_ALTURA_TELA / 3);
		text.setLayoutY(LARGURA_ALTURA_TELA / 6);
		

		buttonNovoJogo = new Button("Novo Jogo");
		buttonNovoJogo.setOnMouseClicked(e -> cliqueNovoJogo(e));
		buttonNovoJogo.setVisible(false);
		
		root.getChildren().add(text);
		root.getChildren().add(buttonNovoJogo);
		stage.setTitle("Jogo da Velha");
		stage.setScene(scene);
		stage.show();
	}

	private void criaBoard() {
		for (int i = 1; i <= 9; i++) {
			Button quadrado = new Button();
			quadrado.setId("" + i);
			quadrado.setMinHeight(TAMANHO_QUADRADO);
			// quadrado.setPadding(new Insets(20));
			quadrado.setLineSpacing(20);
			quadrado.setMinWidth(TAMANHO_QUADRADO);
			quadrado.setBackground(devolveImagem("/fundo.png"));
			if (i <= 3) {
				quadrado.setLayoutX(i * TAMANHO_QUADRADO);
				quadrado.setLayoutY(TAMANHO_QUADRADO);
			}
			if (i >= 4 && i <= 6) {
				quadrado.setLayoutX((i - 3) * TAMANHO_QUADRADO);
				quadrado.setLayoutY(TAMANHO_QUADRADO * 2);
			}
			if (i >= 7) {
				quadrado.setLayoutX((i - 6) * TAMANHO_QUADRADO);
				quadrado.setLayoutY(TAMANHO_QUADRADO * 3);
			}
			quadrado.setOnMouseClicked(e -> cliqueRelizado(e));
			root.getChildren().add(quadrado);
		}
	}

	private Background devolveImagem(String url) {
		BackgroundImage myBI = new BackgroundImage(new Image(url, FUNDO, FUNDO, false, true),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
				BackgroundSize.DEFAULT);
		return new Background(myBI);
	}

	private void cliqueRelizado(MouseEvent event) {
		Button source = (Button) event.getSource();
		if (source.getText().isEmpty()) {
			jogoContinua(source);
		} else {
			jogadaNaoPermitida();
		}
	}

	private void jogoContinua(Button source) {
		limpaTexto();
		realizaJogada(source);
		if (temVencedor()) {
			mostraTextoVencedor();
			desabilitaTodosBotoes(true);
			mostraBotaoNovoJogo();
		}
	}
	
	private void mostraBotaoNovoJogo() {
		buttonNovoJogo.setVisible(true);
	}
	
	private void cliqueNovoJogo(MouseEvent event) {	
		
		buttonNovoJogo.setVisible(false);
		desabilitaTodosBotoes(false);
		limpaBackgroundBotoes();
		limpaTexto();
		mapa.clear();
	}

	private void mostraTextoVencedor() {
		text.setText("Parabêns");
		text.setFont(new Font(40));
		text.setStroke(Color.GREEN);
	}
	
	private void desabilitaTodosBotoes(boolean trueFalse) {
		for(Node node : root.getChildren()) {
			if(node instanceof Button) {
				Button botao = (Button)node;
				botao.setDisable(trueFalse);
				if(NOVO_JOGO.equals(botao.getText())) {
					botao.setDisable(false);	
				}
			}
		}
	}
	
	private void limpaBackgroundBotoes() {
		for(Node node : root.getChildren()) {
			if(node instanceof Button) {
				Button botao = (Button)node;
				if(!NOVO_JOGO.equals(botao.getText())) {
					botao.setBackground(devolveImagem("/fundo.png"));
					botao.setText("");
				}
			}
		}
	}
	
	private void realizaJogada(Button botao) {
		quantidadeJogadas++;

		botao.setTextFill(Color.TRANSPARENT);
		
		if (quantidadeJogadas % 2 == 0) {
			botao.setText("X");
			botao.setBackground(devolveImagem("/jogadorX.png"));
		} else {
			botao.setText("O");
			botao.setBackground(devolveImagem("/jogadorO.png"));
		}
		mapa.put(botao.getId(), botao.getText());
	}

	private void jogadaNaoPermitida() {
		text.setText("Jogada não permitida");
		text.setFont(new Font(20));
		text.setStroke(Color.BROWN);

		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.1), text);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);
		fadeTransition.setCycleCount(Animation.INDEFINITE);
		fadeTransition.play();
	}

	private void limpaTexto() {
		text.setText("");
	}

	private boolean temVencedor() {
		String horizontal1 = mapa.get("1") + mapa.get("2") + mapa.get("3");
		String horizontal2 = mapa.get("4") + mapa.get("5") + mapa.get("6");
		String horizontal3 = mapa.get("7") + mapa.get("8") + mapa.get("9");
		String vertical1 = mapa.get("1") + mapa.get("4") + mapa.get("7");
		String vertical2 = mapa.get("2") + mapa.get("5") + mapa.get("8");
		String vertical3 = mapa.get("3") + mapa.get("6") + mapa.get("9");
		String diagonal1 = mapa.get("1") + mapa.get("5") + mapa.get("9");
		String diagonal2 = mapa.get("3") + mapa.get("5") + mapa.get("7");

		if (horizontal1.equals(GANHOU_X) || horizontal1.equals(GANHOU_O)) {
			System.out.println("horizontal1: " + horizontal1);
			return true;
		}
		if (horizontal2.equals(GANHOU_X) || horizontal2.equals(GANHOU_O)) {
			System.out.println("horizontal2: " + horizontal2);
			return true;
		}
		if (horizontal3.equals(GANHOU_X) || horizontal3.equals(GANHOU_O)) {
			System.out.println("horizontal3: " + horizontal3);
			return true;
		}
		if (vertical1.equals(GANHOU_X) || vertical1.equals(GANHOU_O)) {
			System.out.println("vertical1: " + vertical1);
			return true;
		}
		if (vertical2.equals(GANHOU_X) || vertical2.equals(GANHOU_O)) {
			System.out.println("vertical2: " + vertical2);
			return true;
		}
		if (vertical3.equals(GANHOU_X) || vertical3.equals(GANHOU_O)) {
			System.out.println("vertical3: " + vertical3);
			return true;
		}
		if (diagonal1.equals(GANHOU_X) || diagonal1.equals(GANHOU_O)) {
			System.out.println("diagonal1: " + diagonal1);
			return true;
		}
		if (diagonal2.equals(GANHOU_X) || diagonal2.equals(GANHOU_O)) {
			System.out.println("diagonal2: " + diagonal2);
			return true;
		}
		return false;
	}

}
