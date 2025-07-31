function onInit()
	print("Initialized Test2 Script")

	if entity:hasComponent("RigidBody2DComponent") then
		print("Entity Has RigidBody2D")
	end
end

function onDestroy()
	print("Destroying Test2 Script")
end

function onUpdate(dt)
	--print("Running Test2 Script: ".. dt)
	--local rb = entity:getComponent("RigidBody2DComponent")
	--rb.mass = rb.mass - dt
	--entity:setComponent("RigidBody2DComponent", rb)
end
