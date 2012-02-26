package fr.ipefix.vimes;

import java.io.IOException;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

class GitFile {

	private Repository repository;
	private String path;

	GitFile(Repository repository, String path) {
		this.repository = repository;
		this.path = path;
	}

	public String getContent() throws MissingObjectException, IOException {
		ObjectId head = this.repository.resolve(Constants.HEAD);
		RevWalk revWalk = new RevWalk(this.repository);
		RevCommit commit = revWalk.parseCommit(head);
		TreeWalk tree = TreeWalk.forPath(this.repository, this.path,
				commit.getTree());
		if ( tree == null ) {
			return null;
		}
		ObjectReader reader = tree.getObjectReader();
		ObjectLoader loader = reader.open(tree.getObjectId(0));
		return new String(loader.getBytes());
	}

}
