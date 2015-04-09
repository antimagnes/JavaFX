//@Grab('org.codehaus.groovyfx:groovyfx:0.3.1')
import static groovyx.javafx.GroovyFX.start

final int ROW_COUNT   = 10
final int COL_COUNT   = 10
final int RADIUS      = 20
final int DIAMETER    = 2 * RADIUS
final int MOVE_WAY    = 8 * DIAMETER
final int WIDTH       = COL_COUNT * DIAMETER + MOVE_WAY
final int HEIGHT      = ROW_COUNT * DIAMETER
final int PADDING     = 20
final int OFFSET      = PADDING + RADIUS
final int RAIL_HEIGHT = 10

start {
    stage( title: 'GroovyFX Abacus with Binding', visible: true ) {
        scene( width: WIDTH + 2 * PADDING, height: HEIGHT + 2 * PADDING ) {
            (0..<ROW_COUNT).each { int row ->
                rectangle( x:PADDING, y:OFFSET - (RAIL_HEIGHT / 2) + (row * DIAMETER),
                        width:WIDTH, height:RAIL_HEIGHT )
                // Use an inject to keep track of the last circle
                (0..<COL_COUNT).inject( null ) { lastCircle, column ->
                    circle( radius : RADIUS - 1,
                            centerX: OFFSET + ( column * DIAMETER ),
                            centerY: OFFSET + (row * DIAMETER) ).with { thisCircle ->
                        thisCircle.onMouseClicked = { e ->
                            int newX = thisCircle.translateX > 1 ? 0 : MOVE_WAY
                            translateTransition( 200.ms, node:thisCircle, toX:newX ).playFromStart()
                        } as javafx.event.EventHandler
                        text( x: thisCircle.centerX - 3, y: thisCircle.centerY + 4,
                                text:"${(COL_COUNT - column) % COL_COUNT}",
                                fill: WHITE,
                                translateX: bind { thisCircle.translateX },
                                onMouseClicked: thisCircle.onMouseClicked )
                        if( lastCircle ) {
                            lastCircle.translateXProperty().addListener( { observableValue, oldX, newX ->
                                if( newX > thisCircle.translateX ) thisCircle.translateX = newX
                            } as javafx.beans.value.ChangeListener )
                            thisCircle.translateXProperty().addListener( { observableValue, oldX, newX ->
                                if( newX < lastCircle.translateX ) lastCircle.translateX = newX
                            } as javafx.beans.value.ChangeListener )
                        }
                        thisCircle
                    }
                }
            }
        }
    }
}