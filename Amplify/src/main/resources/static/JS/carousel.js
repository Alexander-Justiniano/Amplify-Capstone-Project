document.addEventListener('DOMContentLoaded', function() {
    const elements = document.querySelectorAll('.element');
    const middleIndex = Math.floor(elements.length / 2);
    let visibleSet = [middleIndex - 1, middleIndex, middleIndex + 1];
    let lastDirection = 'down'; // Track the last scroll direction

    function showElements(direction) {
        // Determine the transition effect based on the scroll direction
        const transitionEffect = direction === 'up' ? 'carousel-slide-left' : 'carousel-slide-right';

        // Remove transition classes from all elements
        elements.forEach(el => {
            el.classList.remove('carousel-slide-left', 'carousel-slide-right');
        });

        // Add transition classes to visible elements
        visibleSet.forEach(index => {
            elements[index].classList.add(transitionEffect);
        });
    }

    // Initially hide all elements except the middle three
    elements.forEach((el, index) => {
        if (!visibleSet.includes(index)) {
            el.style.opacity = '0';
            el.style.transform = 'translateX(-100vw)';
        }
    });

    document.addEventListener('wheel', function(event) {
        const prevSet = [...visibleSet]; // Copy the current visible set before changing it

        if (event.deltaY < 0) { // Scrolling up
            if (visibleSet[0] > 0) {
                visibleSet = visibleSet.map(index => index - 1);
            }
            lastDirection = 'up';
        } else { // Scrolling down
            if (visibleSet[visibleSet.length - 1] < elements.length - 1) {
                visibleSet = visibleSet.map(index => index + 1);
            }
            lastDirection = 'down';
        }

        // Check if the visible set has changed to avoid unnecessary animations
        if (!prevSet.every((val, index) => val === visibleSet[index])) {
            showElements(lastDirection);
        }
    });
});