import java.io.*;
import java.nio.*;
import java.util.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Agent {
    byte[] state_in;
    byte[] old_state_in;
    ByteBuffer state;
    ByteBuffer old_state;
    int action;
    int next_action;
    int life;
    int prev_life;
    double reward;
    int state_x_size = 15;
    int state_y_size = 15;
    Map<ByteBuffer, Double[]> qfunc;
    int num_action = 5;
    Boolean first = true;
    double total_reward;
    Cellular cellular;
    Player player;

    Agent() {
        total_reward = 0;
        this.qfunc = new HashMap<ByteBuffer, Double[]>();

    }

    void init(Player player, Cellular cellular) {
        this.first = true;
        this.cellular = cellular;
        this.state_in = new byte[this.state_x_size * this.state_y_size];
        this.old_state_in = new byte[this.state_x_size * this.state_y_size];
        this.player = player;

    }

    void Observe() {

        getState();
        getReward();
        updateQFunction();

    }

    void getState() {
        for (int i = 0; i < this.state_x_size; i++) {
            for (int j = 0; j < this.state_y_size; j++) {
                if (!this.first) {
                    this.old_state_in[i * this.state_x_size + j] = this.state_in[i * this.state_x_size + j];
                } else {
                    this.old_state_in[i * this.state_x_size + j] = 0;
                }

            }
        }
        for (int i = 0; i < this.state_x_size; i++) {
            for (int j = 0; j < this.state_y_size; j++) {
                int x = player.x + player.xSize / 2 - this.state_x_size / 2 + i;
                int y = player.y + player.ySize / 2 - this.state_y_size / 2 + j;
                if (0 <= x && x <= cellular.xSize && 0 <= y && y <= cellular.ySize) {
                    this.state_in[i * this.state_x_size + j] = (byte) (this.cellular.cells[x][y].nextState);
                } else {
                    this.state_in[i * this.state_x_size + j] = (byte) (5);
                }

            }
        }
        this.old_state = ByteBuffer.wrap(this.old_state_in);
        this.state = ByteBuffer.wrap(this.state_in);
        if (!this.first) {
            // ByteBufferUtility.printByteBuffer(this.old_state);
        }
        this.prev_life = this.life;
        this.life = this.player.life;
        this.first = false;
    }

    void getReward() {
        if (life < prev_life) {
            this.reward = -1;
        }
        if (this.cellular.gameOver) {
            this.reward = -5;
        }
        if (this.cellular.clearFlug)
            this.reward = 10;
    }

    void updateQFunction() {
        double lambda = 0.5;
        double alpha = 0.8;
        double maxQ = Double.MIN_VALUE;
        Double[] newQ = new Double[this.num_action];

        if (!this.qfunc.containsKey(this.old_state)) {

            Double[] aaa = { 0.0, 0.0, 0.0, 0.0, 0.0 };
            qfunc.put(this.old_state, aaa);
        }
        if (!this.qfunc.containsKey(this.state)) {
            Double[] aaa = { 0.0, 0.0, 0.0, 0.0, 0.0 };
            qfunc.put(this.state, aaa);
        }

        newQ = this.qfunc.get(this.old_state);

        for (int i = 0; i < this.num_action; i++) {
            if (maxQ < (this.qfunc.get(this.state))[i]) {
                maxQ = (this.qfunc.get(this.state))[i];
            }
        }

        double temporaldifference = reward + lambda * maxQ - this.qfunc.get(this.old_state)[action];

        newQ[this.action] += alpha * temporaldifference;
        this.qfunc.put(this.old_state, newQ);
    }

    int policy() {

        double maxQ = Double.MIN_VALUE;
        // ByteBufferUtility.printByteBuffer(this.state);
        System.out.println("--------------Qfunc--------------");
        for (int i = 0; i < this.num_action; i++) {
            System.out.println((this.qfunc.get(this.state))[i]);
            if (maxQ < (this.qfunc.get(this.state))[i]) {

                maxQ = this.qfunc.get(this.state)[i];
                this.action = i;
            }
        }
        if (Math.random() < 0.8) {
            return this.action;
        } else {
            return this.action = (int) (Math.random() * this.num_action);
        }
    }
}
