// -*- mode: java; c-basic-offset: 2; -*-
// Copyright 2009-2011 Google, All Rights reserved
// Copyright 2011-2012 MIT, All rights reserved
// Released under the Apache License, Version 2.0
// http://www.apache.org/licenses/LICENSE-2.0

package com.google.appinventor.client.editor.simple.components;

import com.google.appinventor.client.editor.simple.SimpleEditor;
import com.google.appinventor.client.editor.simple.components.utils.SVGPanel;
import com.google.appinventor.client.output.OdeLog;
import com.google.appinventor.components.common.ComponentConstants;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * Mock Slider component.
 *
 * @author M. Hossein Amerkashi - kkashi01@gmail.com
 */
public final class MockSlider extends MockVisibleComponent {

  /**
   * Component type name.
   */
  public static final String TYPE = "Slider";

  //private static final int DEFAULT_WIDTH = 70;

  // Widget for showing the mock slider
  protected final HorizontalPanel panel;
  public String trackColorActive = "lime"; // color should be default when new color added //color of the thumb
  public String trackColorInactive = "lightgray";
  private boolean initialized = false;

  public SVGPanel sliderGraphic;
  int sliderWidth;

  /**
   * Creates a new MockSlider component.
   *
   * @param editor editor of source file the component belongs to
   */
  public MockSlider(SimpleEditor editor) {
    super(editor, TYPE, images.slider());

    // Initialize mock slider UI
    panel = new HorizontalPanel();
    panel.setStylePrimaryName("ode-SimpleMockComponent");
    initComponent(panel);
    paintSlider();
  }

  /**
   * Draw the SVG graphic of the slider. It displays the left and
   * right sides of the slider, each with their own colors.
   *
   */
  private void paintSlider() {
    if (initialized) {
      panel.remove(sliderGraphic);
    } else {
      initialized = true;
    }
    sliderGraphic = new SVGPanel();
    int sliderHeight = 14;  // pixels (Android asset is 28 px at 160 dpi)

    sliderGraphic.setWidth(sliderWidth + "px");
    sliderGraphic.setHeight(sliderHeight + "px");
    OdeLog.log("111111111 "+ sliderWidth);
    sliderGraphic.setInnerSVG("<g id=\"Group_1\" data-name=\"Group 1\" transform=\"translate(-466 -210)\">\n" +
            "    <rect id=\"TrackLeft\" width=\""+ (sliderWidth/2) + "\" height=\"3\"  transform=\"translate(466 213)\" fill=\"" + trackColorActive + "\"/>\n" +
            "    <rect id=\"TrackRight\" width= \"" + (sliderWidth/2) +  "\" height=\"1\" transform=\"translate(506 213)\" fill=\"" + trackColorInactive +"\"/>\n" +
            "    <circle id=\"Thumb\" cx=\"" + (sliderWidth/2) + "cy=\"0\" r=\"7\" transform=\"translate(503 210)\" fill=\"#80cdc6\"/>\n" +
            "  </g>");
    panel.add(sliderGraphic);
    panel.setCellWidth(sliderGraphic, sliderWidth + "px");
    panel.setCellHorizontalAlignment(sliderGraphic, HasHorizontalAlignment.ALIGN_LEFT);
    panel.setCellVerticalAlignment(sliderGraphic, HasVerticalAlignment.ALIGN_MIDDLE);
    refreshForm();
  }

  private void resizeSliderWidth(String width) {
    int newWidth = Integer.parseInt(width);
    if (newWidth == LENGTH_FILL_PARENT) {
      //sliderWidth = 1000;
      //sliderGraphic.setWidth("100%");
    } else if (newWidth == LENGTH_PREFERRED) {
      //sliderWidth = ;
      OdeLog.log("222222222 src= 600 ");
    } else {
      sliderWidth = newWidth;
    }
    paintSlider();
  }

  /**
   * Set track color for slider on the left side of the thumb
   * Thumb is the button that slides back and forth on the slider
   *
   */
  private void setTrackColorActiveProperty(String text) {
    if (sliderGraphic != null) {
      trackColorActive = MockComponentsUtil.getColor(text).toString();
      paintSlider();
    }
  }

  /**
   * Set track color for slider on the right side of the thumb
   * Thumb is the button that slides back and forth on the slider
   *
   */
  private void setTrackColorInactiveProperty(String text) {
    if (sliderGraphic != null) {
      trackColorInactive = MockComponentsUtil.getColor(text).toString();
      paintSlider();
    }
   }

  @Override
  protected boolean isPropertyVisible(String propertyName) {
    //We don't want to allow user to change the slider height. S/he can only change the
    //slider width
    if (propertyName.equals(PROPERTY_NAME_HEIGHT)) {
      return false;
    }
    return super.isPropertyVisible(propertyName);
  }

  // PropertyChangeListener implementation
  @Override
  public void onPropertyChange(String propertyName, String newValue) {
    super.onPropertyChange(propertyName, newValue);

    // Apply changed properties to the mock component
    if (propertyName.equals(PROPERTY_NAME_WIDTH)) {
      resizeSliderWidth(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_COLORLEFT)) {
      setTrackColorActiveProperty(newValue);
      refreshForm();
    } else if (propertyName.equals(PROPERTY_NAME_COLORRIGHT)) {
      setTrackColorInactiveProperty(newValue);
      refreshForm();
    }

  }
}
