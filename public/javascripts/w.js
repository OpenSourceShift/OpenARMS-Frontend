jQuery(function($){

//  YUI().use('node', 'event', function(Y){

  //The Editor config
  var myConfig = {
      height: '300px',
      width: '600px',
      animate: true,
      dompath: true,
      focusAtStart: true
  };

	//Now let's load the Editor.
 	var myEditor = new YAHOO.widget.Editor('editor', myConfig);

  //Wait for the editor's toolbar to load
  myEditor.on('toolbarLoaded', function() {
    //create the new gutter object
    gutter = new YAHOO.gutter();

    //The Toolbar buttons config
    var flickrConfig = {
	            type: 'push',
	            label: 'Insert Flickr Image',
	            value: 'flickr'
	    }

	    //Add the button to the "insertitem" group
	    myEditor.toolbar.addButtonToGroup(flickrConfig, 'insertitem');

	    //Handle the button's click
	    myEditor.toolbar.on('flickrClick', function(ev) {
	        this._focusWindow();
	        if (ev && ev.img) {
	            //To abide by the Flickr TOS, we need to link back to the image that we just inserted
	            var html = '<a href="' + ev.url + '"><img src="' + ev.img + '" title="' + ev.title + '"></a>';
	            this.execCommand('inserthtml', html);
	        }
	        //Toggle the gutter, so that it opens and closes based on this click.
	        gutter.toggle();
	    });
	    //Create the gutter control
	    gutter.createGutter();
	});
    myEditor.render();
//  });
});

