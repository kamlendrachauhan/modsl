/**
 * Copyright 2008 Andrew Vishnyakov <avishn@gmail.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.modsl.core.agt.model;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.acos;
import static java.lang.Math.signum;

/**
 * Abstract rectangle graph element
 * @author avishnyakov
 */

public abstract class AbstractBox<P extends AbstractElement<?>> extends AbstractElement<P> {

    /**
     * Position
     */
    protected Pt pos = new Pt();

    /**
     * Alt position/displacement (needed for layouts)
     */
    protected Pt disp = new Pt();

    /**
     * Size
     */
    protected Pt size = new Pt();

    /**
     * Create new
     */
    public AbstractBox() {
        //
    }

    /**
     * Create new
     * @param type
     */
    public AbstractBox(MetaType type) {
        super(type);
    }

    /**
     * Create new
     * @param type
     * @param name
     */
    public AbstractBox(MetaType type, String name) {
        super(type, name);
    }

    /**
     * @return angle of the line between this box and the other box
     */
    public double angle(AbstractBox<?> n2) {
        Pt delta = getCtrDelta(n2);
        if (delta.y > 0d) {
            return acos(this.cos(n2));
        } else if (delta.y < 0d) {
            return 2 * PI - acos(this.cos(n2));
        } else {
            if (delta.x >= 0d) {
                return 0;
            } else {
                return PI;
            }
        }
    }

    /**
     * @return cos of angle between 0 and diagonal of this box
     */
    public double cos() {
        return size.x / size.len();
    }

    /**
     * @return cos(angle of the line between this box and the other box)
     */
    public double cos(AbstractBox<?> b2) {
        Pt delta = getCtrDelta(b2);
        return delta.x / delta.len();
    }

    public Pt getDisp() {
        return disp;
    }

    /**
     * @return distance between centers of this box and the other box
     */
    public Pt getCtrDelta(AbstractBox<?> b2) {
        return b2.getCtrPos().minus(getCtrPos());
    }

    /**
     * @return center position (will be different from pos if size > 0)
     */
    public Pt getCtrPos() {
        return pos.plus(size.div(2d));
    }

    /**
     * Connector port position at this box for given angle func values
     * @param sin
     * @param cos
     * @param tan
     * @return connector port position
     */
    Pt getPort(double sin, double cos, double tan) {
        Pt port = new Pt();
        Pt cp = getCtrPos();
        Pt s = getSize();
        if (abs(tan()) > abs(tan)) {
            // i.e. line is crossing this box's side
            port.x = cp.x + s.x * signum(cos) / 2d;
            port.y = cp.y + s.x * tan * signum(cos) / 2d;
        } else {
            port.x = cp.x + s.y / tan * signum(sin) / 2d;
            port.y = cp.y + s.y * signum(sin) / 2d;
        }
        return port;
    }

    /**
     * @param b2 other box
     * @return min delta considering the boxes' sizes
     */
    public Pt getPortDelta(AbstractBox<?> b2) {
        Pt[] res = getPorts(b2);
        return res[0].decBy(res[1]);
    }

    /**
     * @param b2 other box
     * @return port connector points if this box is connected to another box by
     * a straight line
     */
    public Pt[] getPorts(AbstractBox<?> b2) {
        Pt[] res = new Pt[2];
        res[0] = this.getPort(this.sin(b2), this.cos(b2), this.tan(b2));
        res[1] = b2.getPort(b2.sin(this), b2.cos(this), b2.tan(this));
        return res;
    }

    /**
     * @return position (top left corner for objects w/ size > 0)
     */
    public Pt getPos() {
        return pos;
    }

    /**
     * @return size
     */
    public Pt getSize() {
        return size;
    }

    /**
     * @param b other box
     * @return true if connected directly (no intermediate bends or nodes)
     */
    public boolean isConnectedTo(AbstractBox<?> b) {
        return false;
    }

    public void setDisp(Pt altPos) {
        this.disp = altPos;
    }

    /**
     * Set position (top left corner)
     * @param pos
     */
    public void setPos(Pt point) {
        this.pos = point;
    }

    /**
     * Set size
     * @param size
     */
    public void setSize(Pt size) {
        this.size = size;
    }

    /**
     * @return sin of angle between 0 and diagonal of this box
     */
    public double sin() {
        return size.y / size.len();
    }

    /**
     * @return sin(angle of the line between this box and the other box)
     */
    public double sin(AbstractBox<?> b2) {
        Pt delta = getCtrDelta(b2);
        return delta.y / delta.len();
    }

    /**
     * @return tan of angle between 0 and diagonal of this box
     */
    public double tan() {
        return size.y / size.x;
    }

    /**
     * @return tan(angle of the line between this box and the other box)
     */
    public double tan(AbstractBox<?> b2) {
        Pt delta = getCtrDelta(b2);
        return delta.y / delta.x;
    }

}