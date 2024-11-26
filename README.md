# TeamProject2019_03

Foodu - university project developed in Java and Flutter

We’ve decided on a mobile app that combines the likes of Tinder and Yelp to match customers with restaurants,
 then facilitate the ordering process for pre-orders.

A common user story would be a user would like food to eat at a restaurant (the distinction between finding a restaurant and finding food at a restaurant is important).
When the user sets up their account, they provide their name, login details, profile picture, bank details, and default range.The user would then either select a category of food,
or search by keyword for what they’d like to eat. The user must also be able to modify their range/location/meal time. The user is then taken to a screen to swipe through pictures of food, swiping left to skip pictures, 
and swiping right when they’ve found a dish that looks good. When a user swipes right, details about the restaurant and the dish will display
(i.e. restaurant name, contact, location, average rating, dish name, price, rating, user comments, ingredients, and preparation time) then giving the user the option to order this food prior to going to the restaurant
(along with an option to add more food to the order). If a user wants to add more items to their order, a new page is displayed for selecting menu sorted menu items.
The user will then pay for the order before the order is confirmed and the user is given a time to be at the restaurant. After the food has been served, the user will be encouraged/notified to give a rating, which will be viewed by other users later.
User should be able to access a record of their previous meals. Users will also be given the choice to add a custom tip after they have ordered their food when they rate their meal.

The restaurant's client will then receive the order (i.e. dish ordered, client name, time of order, time order is to be served, and a receipt) and they must accept or decline the order.
The restaurant client should also be able to modify the menu and their profile. Restaurant clients can purchase a premium service to promote their dishes in the application,
 increasing the likelihood that their product is viewed by customers. 

There should be a client for an administrator to manage comments, ratings, modify restaurant and dish details, manage and remove images. 

## Contributing Guide
### Introduction
This section of the README is allocated to describing the methodologies we for collaboration as a team.
These include our git branching model, our scrum methodology, and general guidelines for communication.

### Git Branching
Our git branching model is very similar to [the one found here](https://nvie.com/posts/a-successful-git-branching-model/), however there are some differences that will be highlighted here.

#### Committing and Pushing
Code should be frequently committed and pushed. This means you should commit code that is _relatively_ working, as in the commit should have some meaningful change that doesn't break the project.
When changes have been made and committed, you need to pull any changes made to the remote branch while you were working, then push your changes. The following is an example of this workflow.

`$> git add myfile.txt`

`$> git commit -m "Commit Message"`

`$> git pull`

`$> git push`

Commit messages should also follow [these](https://chris.beams.io/posts/git-commit/) conventions:
- Separate subject from body with a blank line.
- Limit the subject line to 50 characters.
- Capitalize the subject line.
- Do not end the subject line with a period.
- Use the imperative mood in the subject line.
- Wrap the body at 72 characters.
- Use the body to explain what and why vs. how.

#### Branching
There are two important branches that _**should not**_ be directly committed to (even to fix issues related to someone making a direct commit or accidental merge). These include:
- `master`: This is where the latest working version of our code sits (and where tags are cut)
- `Devel`: This is where new features are merged into

When a feature is developed, a feature branch is branched from `Devel`. The name of the branch should also be **short, concise, and should be categorized** by the following:
- `feat/`: A branch containing the development of a new feature
- `wip/`: A branch with *a lot* of work that won't be finished soon
- `bug/`: A branch for a bug fix
- `junk/`: A throw away branch

When naming a branch, use a `/` to separate the description from the categorization, and `-` to replace spaces. The following is an example of this:
`feat/my-feature-branch`

It should be noted that a branch should **only contain changes related to the purpose of the branch**, which should be clear from the branch's name.

It's important that branches are **short-lived** to increase productivity and reduce the number of merge conflicts. 
With that being said, the work to complete a card on Trello doesn't need to exist in one branch and should be spread out amongst several branches.
In order to keep track of branches you need to **attach your branch** to the card you're working on.

When the work on a branch is completed, it will be **merged back** into `Devel` for it to be rolled out at the end of the sprint.
Furthermore, at the end of the sprint the `Devel` branch will be merged with `master` **cutting a new tag**.

If a severe bug is found in `Devel` that is also present in `master`, a *hotfix* **branch will be created to fix the bug**, which will then be merged into both `Devel` and `master`

The following is a typical workflow for creating a new branch:

`$> git checkout Devel`

`$> git pull`

`$> git checkout -b feat/my-feature`

`$> git push -u origin feat/my-feature`

#### Merging
Merging happens in two cases:
- During a sync merge
- When there's a dependency on another branch (this should be avoided)

A sync merge should be done on a **simi-regular basis**. The most important time to sync merge is right **before a pull request** to resolve any possible conflicts.
A sync merge in preparation for a pull request would be as follows:

`$> git merge Devel`

`$> git mergetool` If there are merge conflicts

`$> git pull`

`$> git push` 

#### Pull Requests
Pull requests will **require** reviews one person to merge into `Devel`, and two reviews to merge into `master`. Pull requests don't necessarily have to be merged chronologically, 
pull requests that go stale or generate merge conflicts after other merges have taken place should be marked with **request for changes**,
where another sync merge will be **performed by the member that opened the pull request** and be re-reviewed for approval.

A reviewer should **merge the code from the feature branch to master** on their local branch, and perform smoke tests to determine if the merge was successful.

#### Tags
Tags are generated at the end of each sprint, cut by merging `Devel` into `master`. Versions are marked by the sprint they were completed in (i.e. sprint 2 would cut 0.2), and the final product at the end of
sprint 5 will be version 1.0.

### Agile
Our team is following the guidelines set out by [this Scrum Primer](http://www.goodagile.com/scrumprimer/scrumprimer20.pdf). The following is a summary of how we've decided to plan and manage ourselves as a team.

#### Daily Scrum
Every day, an automated message is sent to the #dailyscrum channel on our Slack for us to respond to. If there is a planned meeting that day, the daily scrum can be saved for then, however, 
it's recommended to put your response in the channel thread for documentation.
 
 Daily Scrum answers the following questions:
1. What have you done since the last meeting?
2. What do you plan to do before the next meeting?
3. What obstacles are you facing?

As this takes place, the **Scrum Master** tries to help members with the identified issues they've pointed out.

#### Sprint Planning
Sprint planning takes place on the first Monday of each sprint, where the team will meet with the _product owner_ to discuss things to add to the _General Backlog_.
After this has taken place, the _Sprint Backlog_ is created by selecting cards from the _General Backlog_ with the best priority to effort ratio.

#### Sprint Review
The sprint review is a short meeting at the end of every sprint for the team to discuss good/bad things about the previous sprint for the team to reflect on, and hopefully improve from.

#### Backlog Refinement
This takes place the second week of each sprint, and is a time for the team to modify the backlog to:
- Break up tasks too large for one card
- Refine titles/descriptions
- Refine priority
- Refine effort estimates

Refinement of priority and effort are done using [Planning Poker](https://www.planningpoker.com/). 

#### Team Management
The team is self-organising, where members will assign themselves to Trello cards. If more than one person is working on the same card, more focus needs to be placed on communication to prevent issues.
On Trello, the following is a workflow for completing cards:
- Card is placed in _Sprint Backlog_ during _Sprint Planning_
- Assign yourself to the card **when you are about to begin the tasks**
- Move card to _Doing_ when you start your work
- Create (a) branch/branches and **attach it/them to the Trello card**
- Pull Requests should also be added to the card as they are created (and removed when they are resolved)
- You can create/modify _Sprint Tasks_ as unknown unknowns become known
- Mark _Sprint Tasks_ as they are completed and **Reduce the effort estimate** as less work is required to finish the card
- Move the card to _Code Review_ once all of the _Sprint Tasks_ have been completed
- Another team member will review the card, confirm all of the functionality/tests are present in `Devel` (otherwise move the card back to _ToDo_ and describe why this card did not pass)
- Once the card has passed the code review, move the card to _Done_

#### Scrum Master
The _Scrum Master_ is responsible for:
- Making sure team members are supported when they need it
- Making sure the team follows scrum as defined here
- Maintaining the `Devel` and `master` branches, and generally keeping the repository clean

### Style and Testing
Team members should use [Google Checkstyle](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml) when writing _Java_ for the _Server_.
_Dartfrmt_ should be used to format _Dart_ on the _Mobile Client_.

Tests should be written in _client/tests_ before any Dart is written.

### General Communication
- There is no rule against multiple people working on a branch,
  however if you do good communication is required to prevent conflicts and duplicate work.
- Any questions/discussion regarding a task should be asked on the corresponding Trello card.
- Slack is for any general questions/discussion